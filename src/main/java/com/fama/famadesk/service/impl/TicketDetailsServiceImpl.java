package com.fama.famadesk.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchProviderException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.components.rolevalidator.IRoleValidator;
import com.fama.famadesk.components.util.ApplicationUtil;
import com.fama.famadesk.dao.ITicketDetailsDao;
import com.fama.famadesk.enums.Priority;
import com.fama.famadesk.enums.TicketStatus;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.EmailAttachment;
import com.fama.famadesk.model.EmailContent;
import com.fama.famadesk.model.TicketAssignHistory;
import com.fama.famadesk.model.TicketDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AssignTicketRequest;
import com.fama.famadesk.requestobject.ManualTicketCreateRequest;
import com.fama.famadesk.service.IEmailAttachmentService;
import com.fama.famadesk.service.IEmailContentService;
import com.fama.famadesk.service.ITicketAssignHistoryService;
import com.fama.famadesk.service.ITicketDetailsService;
import com.fama.famadesk.service.IUserService;
import com.sun.mail.imap.IMAPFolder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketDetailsServiceImpl implements ITicketDetailsService {

	@Autowired
	private ITicketDetailsDao iTicketDetailsDao;
	@Autowired
	private IEmailAttachmentService iEmailAttachmentService;
	@Autowired
	private TicketDetailsServiceImpl ticketDetailsServiceImpl;
	@Autowired
	private IEmailContentService iEmailContentService;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private ITicketAssignHistoryService iTicketAssignHistoryService;
	@Autowired
	private IRoleValidator iRoleValidator;

//@Scheduled(fixedDelay = 10000)
	public void emailReadInitializer() throws IOException, NoSuchProviderException {
		System.out.println("Scheduler Runing");
		String host = "imap-mail.outlook.com";
		String port = "993";
		String userName = "famadesk@outlook.com";
		String password = "Fama@123";

		try {
			ticketDetailsServiceImpl.searchEmail(host, port, userName, password);
		} catch (InvocationTargetException e) {

			e.printStackTrace();
		}

	}

	public void searchEmail(String host, String port, String userName, String password)
			throws InvocationTargetException, IOException, NoSuchProviderException {
		Properties properties = new Properties();
		properties.put("mail.imap.host", host);
		properties.put("mail.imap.port", port);
		properties.setProperty("mail.imap.ssl.enable", "true");
		Session session = Session.getDefaultInstance(properties);

		try {
			Store store = session.getStore("imap");
			store.connect(host, userName, password);

			IMAPFolder folderInbox = (IMAPFolder) store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

//get unread mail
			Date date = new Date();
			SearchTerm searchStartDate = new ReceivedDateTerm(ComparisonTerm.EQ, date);
			SearchTerm unreadMail = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			SearchTerm term = new AndTerm(searchStartDate, unreadMail);
			Message[] messages = folderInbox.search(term);
			// Message messages[] = folderInbox.getMessages();
			System.out.println("email length    " + messages.length);

			for (int i = 0; i < messages.length; i++) {
				String trimNewEmailUniqueId = null;
				List<String> emailAttachmentPathList = getAttachment(messages[i]);
				long uId = folderInbox.getUID(messages[i]);
				Enumeration<Header> headers = messages[i].getAllHeaders();

				while (headers.hasMoreElements()) {

					Header header = headers.nextElement();
					if (header.getName().equalsIgnoreCase("Message-ID")) {
						String newEmailUniqueId = header.getValue();
						trimNewEmailUniqueId = ApplicationUtil.removeAngleBraces(newEmailUniqueId);
						break;

					} else if (header.getName().equalsIgnoreCase("References")) {

						EmailContent newEmailContent = new EmailContent();
						String replyEmailUniqueId = header.getValue();
						String trimReplyEmailUniqueId = ApplicationUtil.removeAngleBraces(replyEmailUniqueId);
						EmailContent emailContent = iEmailContentService
								.getNewestEmailContentByEmailUniqueId(trimReplyEmailUniqueId);
						String bodyContent = getBodyTextFromMessage(messages[i]);
						String regexAppliedBodyContent = bodyContent
								.split("\\D{2}\\s\\D{3},\\s\\D{3}\\s\\d{1}|{2},\\s\\d{4},\\s\\d{1}{2}:")[0];
						newEmailContent.setContent(regexAppliedBodyContent);
						newEmailContent.setMessageUniqueId(trimReplyEmailUniqueId);
						newEmailContent.setSentOn(messages[i].getReceivedDate());
						newEmailContent.setSubject(messages[i].getSubject().split(": ")[1]);
						newEmailContent
								.setSenderEmail(ApplicationUtil.removeAngleBraces(messages[i].getFrom()[0].toString()));
						newEmailContent.setTicketDetails(emailContent.getTicketDetails());
						iEmailContentService.create(newEmailContent);
						if (emailAttachmentPathList != null) {
							creatAttachment(emailAttachmentPathList, newEmailContent, emailContent.getTicketDetails());
						}
						break;

					}

				}

				if (trimNewEmailUniqueId != null) {

					TicketDetails ticketDetails = new TicketDetails();
					EmailContent newEmailContent = new EmailContent();
					String bodyContent = getBodyTextFromMessage(messages[i]);
					ticketDetails.setTicketId(ApplicationUtil.generateTickedId());
					ticketDetails.setUserName((messages[i].getFrom()[0].toString().split(" <")[0]));
					ticketDetails.setPriority(Priority.LOW);
					ticketDetails.setDescription(bodyContent);
					ticketDetails.setCreatedOn(messages[i].getReceivedDate());
					ticketDetails.setTicketStatus(TicketStatus.PENDING);
					// ticketDetails.setAssociatedService(AssociatedService.SUPPORT);
					ticketDetails.setSubject(messages[i].getSubject());
					ticketDetails.setUniqueId(uId);

					Address address[] = messages[i].getAllRecipients();

					if (address.length > 1) {
						ticketDetails.setCustomerEmail(address[0].toString());
						ticketDetails.setCcEmail(address[1].toString());
					}

					ticketDetails
							.setCustomerEmail(ApplicationUtil.removeAngleBraces(messages[i].getFrom()[0].toString()));
					newEmailContent
							.setSenderEmail(ApplicationUtil.removeAngleBraces(messages[i].getFrom()[0].toString()));
					newEmailContent.setSentOn(messages[i].getReceivedDate());
					newEmailContent.setContent(bodyContent);
					newEmailContent.setSubject(messages[i].getSubject());
					newEmailContent.setMessageUniqueId(trimNewEmailUniqueId);
					newEmailContent.setTicketDetails(ticketDetails);
					iTicketDetailsDao.create(ticketDetails);
					iEmailContentService.create(newEmailContent);
					if (emailAttachmentPathList != null) {
						creatAttachment(emailAttachmentPathList, newEmailContent, ticketDetails);

					}

				}
			}
			System.out.println("---------------------------------------!!!!------------------------------------");
			folderInbox.close();
			store.close();
			System.out.println("-----------Connection closed-------");
		}

		catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void creatAttachment(List<String> emailAttachmentPathList, EmailContent newEmailContent,
			TicketDetails ticketDetails) {
		if (emailAttachmentPathList != null) {
			for (String attachmentPath : emailAttachmentPathList) {
				EmailAttachment emailAttachement = new EmailAttachment();
				emailAttachement.setAttachment(attachmentPath);
				emailAttachement.setEmailContent(newEmailContent);
				emailAttachement.setDate(new Date());
				emailAttachement.setTicketDetails(ticketDetails);
				iEmailAttachmentService.create(emailAttachement);
			}
		}

	}

	private String getBodyTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		System.out.println("message content type  :" + message.getContent().toString());
		System.out.println("mesage check  :" + message.isMimeType("multipart/*"));
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		} else if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}

		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {

		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);

			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break;

			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
				System.out.println("multipart print   :" + result);
			}
		}
		return result;
	}

	public List<String> getAttachment(Message message) throws MessagingException, IOException {
		LocalTime time = LocalTime.now();
		List<String> attachment = new ArrayList<String>();
		// System.out.println(time.getNano());

		Multipart multiPart = (Multipart) message.getContent();

		System.out.println("multipart count       :" + multiPart.getCount());
		for (int i = 0; i < multiPart.getCount(); i++) {
			MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
			if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				String destFilePath = "C:\\Users\\Dharmesh\\Desktop\\output\\" + "-" + time.getNano()
						+ part.getFileName();

				System.out.println("File name " + part.getFileName());
				File file = new File(destFilePath);
				FileOutputStream output = new FileOutputStream(file);
				InputStream input = part.getInputStream();
				byte[] buffer = new byte[4096];
				int byteRead;
				while ((byteRead = input.read(buffer)) != -1) {
					output.write(buffer, 0, byteRead);
				}
				attachment.add(destFilePath);
				output.close();
			}
		}
		return attachment;
	}

	private void preValidationCheck(ManualTicketCreateRequest manualTicketCreateRequest) {
		if (manualTicketCreateRequest.getCustomerPhone() == null
				|| manualTicketCreateRequest.getCustomerPhone().isEmpty()) {
			throw new BusinessValidationException("Mobile number is required");
		}

		if (manualTicketCreateRequest.getDescription() == null
				|| manualTicketCreateRequest.getDescription().isEmpty()) {
			throw new BusinessValidationException("Description is required");
		}

		if (manualTicketCreateRequest.getPriority() == null) {
			throw new BusinessValidationException("Priority is required");
		}

		if (manualTicketCreateRequest.getAssignedTo() == null) {
			throw new BusinessValidationException("User id is required");
		}
	}

	@Override
	public TicketDetails manuallyCreateTicket(ManualTicketCreateRequest manualTicketCreateRequest, User loggedInUser) {

		preValidationCheck(manualTicketCreateRequest);

		TicketDetails anEntity = new TicketDetails();

		anEntity.setPriority(manualTicketCreateRequest.getPriority());
		anEntity.setDescription(manualTicketCreateRequest.getDescription());
		anEntity.setCustomerPhone(manualTicketCreateRequest.getCustomerPhone());
		anEntity.setTicketStatus(TicketStatus.PENDING);
		anEntity.setTicketId(ApplicationUtil.generateTickedId());
		anEntity.setCreatedOn(new Date());
		anEntity.setAddedBy(loggedInUser);

		create(anEntity);

		assignTicketAndMaintainHistory(
				new AssignTicketRequest(anEntity.getId(), manualTicketCreateRequest.getAssignedTo()), loggedInUser);

//		if()
//		EmailContent emailContent = new EmailContent();
//
//		emailContent.setContent(anEntity.getDescription());
//		emailContent.setSenderEmail(anEntity.getCustomerEmail());
//		emailContent.setSubject(anEntity.getSubject());
//		emailContent.setTicketDetails(anEntity);
//		iEmailContentService.create(emailContent);
		return anEntity;
	}

	@Override
	public List<TicketDetails> findAll() {
		return iTicketDetailsDao.findAll();
	}

	@Override
	public TicketDetails findByPk(Integer entityPk) {
		return iTicketDetailsDao.findByPk(entityPk);

	}

	@Override
	public TicketDetails create(TicketDetails anEntity) {
		return iTicketDetailsDao.create(anEntity);
	}

	@Override
	public TicketDetails update(TicketDetails anEntity) {
		return iTicketDetailsDao.update(anEntity);
	}

	@Override
	public TicketDetails update(TicketDetails anEntity, Integer ticketId, Integer assignTo, User loggedInUser) {

		TicketDetails ticketDetails = findByPk(ticketId);
		if (ticketDetails != null) {
			ticketDetails.setCustomerPhone(anEntity.getCustomerPhone());
			ticketDetails.setPriority(anEntity.getPriority());
			ticketDetails.setTicketStatus(anEntity.getTicketStatus());
			ticketDetails.setCustomerPhone(anEntity.getCustomerPhone());
			ticketDetails.setUpdatedBy(loggedInUser);
			TicketDetails updatedTicketDetails = create(ticketDetails);
			if (updatedTicketDetails != null) {
				TicketAssignHistory ticketAssignDetails = new TicketAssignHistory();
				ticketAssignDetails.setUpdatedBy(loggedInUser);
				ticketAssignDetails.setUpdatedOn(new Date());
				iTicketAssignHistoryService.create(ticketAssignDetails);
			}
			return ticketDetails;

		} else {

			throw new BusinessValidationException("ticket id is not found");

		}
	}

	@Override
	public TicketAssignHistory assignTicketAndMaintainHistory(AssignTicketRequest assignTicketRequest,
			User loggedInUser) {

		User assignee = iUserService.findByPk(assignTicketRequest.getAssignedTo());

		TicketDetails ticketDetails = findByPk(assignTicketRequest.getTicketDetailId());

		ticketDetails.setAssignedTo(assignee);
		ticketDetails.setAssignedBy(loggedInUser);

		update(ticketDetails);

		TicketAssignHistory ticketAssignHistory = new TicketAssignHistory();

		ticketAssignHistory.setAssignedOn(new Date());
		ticketAssignHistory.setAssignedTo(assignee);
		ticketAssignHistory.setAssignedBy(loggedInUser);
		ticketAssignHistory.setTicketDetails(ticketDetails);
		iTicketAssignHistoryService.create(ticketAssignHistory);
		return ticketAssignHistory;

	}

	@Override
	public TicketAssignHistory forwardTicketRequest(AssignTicketRequest assignTicketRequest, User loggedInUser) {

		User assignee = iUserService.findByPk(assignTicketRequest.getAssignedTo());

		TicketDetails ticketDetails = findByPk(assignTicketRequest.getTicketDetailId());

		ticketDetails.setUpdatedOn(new Date());
		ticketDetails.setAssignedTo(assignee);
		ticketDetails.setAssignedBy(loggedInUser);
		log.info("Updating ticket details PK ID : {}", assignTicketRequest.getTicketDetailId());
		update(ticketDetails);

		TicketAssignHistory ticketAssignHistory = new TicketAssignHistory();

		ticketAssignHistory.setForwardedOn(new Date());
		ticketAssignHistory.setAssignedTo(assignee);
		ticketAssignHistory.setAssignedBy(loggedInUser);
		ticketAssignHistory.setTicketDetails(ticketDetails);
		iTicketAssignHistoryService.create(ticketAssignHistory);
		log.info("Ticket history created for ticket details PK ID : {}", assignTicketRequest.getTicketDetailId());

		return ticketAssignHistory;

	}

	@Override
	public TicketDetails findByticketId(String ticketId) {
		return iTicketDetailsDao.findByticketId(ticketId);
	}

	@Override
	public void markTicketRequestAsCompleted(Integer ticketDetailId, User loggedInUser) {
		TicketDetails ticketDetails = findByPk(ticketDetailId);
		ticketDetails.setUpdatedOn(new Date());
		ticketDetails.setTicketStatus(TicketStatus.COMPLETED);
		ticketDetails.setUpdatedBy(loggedInUser);
		update(ticketDetails);

	}

	@Override
	public List<TicketDetails> getPendingTicketByAssignee(Integer assignedTo) {

		return iTicketDetailsDao.getPendingTicketByAssignee(assignedTo);
	}

	@Override
	public long getPendingTicketCount(User loggedInUser) {

		if (iRoleValidator.validateAdminRole(loggedInUser.getRoleName())) {
			return iTicketDetailsDao.getPendingTicketCount();
		}

		else if (iRoleValidator.validateAgentRole(loggedInUser.getRoleName())) {
			return iTicketDetailsDao.getPendingTicketCountByAssignee(loggedInUser.getUserid());
		} else {
			log.info("Unhandled role : {} is received ", loggedInUser.getRoleName());
			return 0l;
		}
	}

}
