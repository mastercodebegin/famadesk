//package com.fama.famadesk.service.impl;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.security.NoSuchProviderException;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.Properties;
//
//import javax.mail.Address;
//import javax.mail.BodyPart;
//import javax.mail.Flags;
//import javax.mail.Folder;
//import javax.mail.Header;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.Part;
//import javax.mail.Session;
//import javax.mail.Store;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMultipart;
//import javax.mail.search.AndTerm;
//import javax.mail.search.ComparisonTerm;
//import javax.mail.search.FlagTerm;
//import javax.mail.search.ReceivedDateTerm;
//import javax.mail.search.SearchTerm;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.fama.famadesk.components.util.ApplicationUtil;
//import com.fama.famadesk.dao.IEmailContentDao;
//import com.fama.famadesk.dao.ITicketDetailsDao;
//import com.fama.famadesk.enums.AssociatedService;
//import com.fama.famadesk.enums.Priority;
//import com.fama.famadesk.enums.TicketStatus;
//import com.fama.famadesk.model.EmailAttachment;
//import com.fama.famadesk.model.EmailContent;
//import com.fama.famadesk.model.TicketAssignDetails;
//import com.fama.famadesk.model.TicketDetails;
//import com.fama.famadesk.model.User;
//import com.fama.famadesk.service.IEmailAttachementService;
//import com.fama.famadesk.service.IEmailContentService;
//import com.fama.famadesk.service.ITicketDetailsService;
//import com.fama.famadesk.service.IUserService;
//import com.sun.mail.imap.IMAPFolder;
//
////@Service
//@EnableScheduling
////@SuppressWarnings("unused")
//public class OldWorkingTicketDetailsServiceImpl implements ITicketDetailsService {
//
//	@Autowired
//	ITicketDetailsDao iTicketDetailsDao;
//	@Autowired
//	IEmailAttachementService iEmailAttachementService;
//	@Autowired
//	OldWorkingTicketDetailsServiceImpl ticketDetailsServiceImpl;
//	@Autowired
//	IEmailContentService iEmailContentService;
//	@Autowired
//	IUserService iUserService;
//
//	@Scheduled(fixedDelay = 10000)
//	public void emailReadInitializer() throws IOException, NoSuchProviderException {
//		System.out.println("Scheduler Runing");
//		String host = "imap-mail.outlook.com";
//		String port = "993";
//		String userName = "famadesk@outlook.com";
//		String password = "Fama@123";
//
//		try {
//			ticketDetailsServiceImpl.searchEmail(host, port, userName, password);
//		} catch (InvocationTargetException e) {
//
//			e.printStackTrace();
//		}
//
//	}
//
//	public void searchEmail(String host, String port, String userName, String password)
//			throws InvocationTargetException, IOException, NoSuchProviderException {
//		Properties properties = new Properties();
//		System.out.println("content id-----------------" + ApplicationUtil.generatePassword());
//		properties.put("mail.imap.host", host);
//		properties.put("mail.imap.port", port);
//		properties.setProperty("mail.imap.ssl.enable", "true");
//		Session session = Session.getDefaultInstance(properties);
//		// Long Uid=null;
//
//		try {
//			Store store = session.getStore("imap");
//			store.connect(host, userName, password);
//
//			IMAPFolder folderInbox = (IMAPFolder) store.getFolder("INBOX");
//			// UIDFolder uf = (UIDFolder) folderInbox;
//			folderInbox.open(Folder.READ_WRITE);
//
////get unread mail
//			Date date = new Date();
//			SearchTerm searchStartDate = new ReceivedDateTerm(ComparisonTerm.EQ, date);
//			SearchTerm unreadMail = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
//			SearchTerm term = new AndTerm(searchStartDate, unreadMail);
//			Message[] messages = folderInbox.search(term);
//			// Message messages[] = folderInbox.getMessages();
//			System.out.println("email length    " + messages.length);
//
//			for (int i = 0; i < messages.length; i++) {
//				String trimNewEmailUniqueId = null;
//				long uId = folderInbox.getUID(messages[i]);
//				System.out.println("Uid------" + uId);
//				Enumeration<Header> headers = messages[i].getAllHeaders();
//
//				while (headers.hasMoreElements()) {
//
//					Header header = headers.nextElement();
//					// System.out.println("Header name --- " + header.getName());
//					// System.out.println("Header value --- " + header.getValue());
//					if (header.getName().equalsIgnoreCase("Message-ID")) {
//						String newEmailUniqueId = header.getValue();
//						trimNewEmailUniqueId = ApplicationUtil.getRemoveAngleBraces(newEmailUniqueId);
//						System.out.println("Header name Msgid--- " + header.getName());
//						System.out.println("Header value --- " + header.getValue());
//						System.out.println("newEmailuniqueId      :" + trimNewEmailUniqueId);
//						// List<EmailContent> emailContent = iEmailContentService
//						// .getEmailContentByMessageUniqueId(trimNewEmailUniqueId);
//						break;
//					} else if (header.getName().equalsIgnoreCase("References")) {
//
//						System.out.println("Header name Reference --- " + header.getName());
//						System.out.println("Header value --- " + header.getValue());
//						EmailContent newEmailContent = new EmailContent();
//
//						String replyEmailUniqueId = header.getValue();
//						String trimReplyEmailUniqueId = ApplicationUtil.getRemoveAngleBraces(replyEmailUniqueId);
//						EmailContent emailContent = iEmailContentService
//								.getNewestEmailContentByEmailUniqueId(trimReplyEmailUniqueId);
//						System.out.println("email content " + emailContent);
//						List<String> attachment = getAttachment(messages[i]);
//
//						String bodyContent = getBodyTextFromMessage(messages[i]);
//						// System.out.println("Body Content :" + bodyContent);
//						String regexAppliedBodyContent = bodyContent
//								.split("\\D{2}\\s\\D{3},\\s\\D{3}\\s\\d{1}|{2},\\s\\d{4},\\s\\d{1}{2}:")[0];
//						System.out.println("after regx body content:" + regexAppliedBodyContent);
//						newEmailContent.setContent(regexAppliedBodyContent);
//						newEmailContent.setMessageUniqueId(trimReplyEmailUniqueId);
//						newEmailContent.setSentOn(messages[i].getReceivedDate());
//						newEmailContent.setSubject(messages[i].getSubject().split(": ")[1]);
//						newEmailContent.setSenderEmail(
//								ApplicationUtil.getRemoveAngleBraces(messages[i].getFrom()[0].toString()));
//						newEmailContent.setTicketDetails(emailContent.getTicketDetails());
//						iEmailContentService.create(newEmailContent);
//						if (attachment != null) {
//							for (String emailAttachment : attachment) {
//								EmailAttachment emailAttachement = new EmailAttachment();
//								emailAttachement.setAttachment(emailAttachment);
//								emailAttachement.setEmailContent(newEmailContent);
//								emailAttachement.setDate(new Date());
//								emailAttachement.setTicketDetails(emailContent.getTicketDetails());
//								iEmailAttachementService.create(emailAttachement);
//
//							}
//
//						}
//
//						System.out.println("check replyemailcontent is saved");
//						break;
//
//					}
//
//				}
//
//				if (trimNewEmailUniqueId != null) {
//
//					List<String> attachment = getAttachment(messages[i]);
//					TicketDetails ticketDetails = new TicketDetails();
//					EmailContent newEmailContent = new EmailContent();
//					String bodyContent = getBodyTextFromMessage(messages[i]);
//					ticketDetails.setTicketId(ApplicationUtil.generateTickedId());
//					ticketDetails.setUserName((messages[i].getFrom()[0].toString().split(" <")[0]));
//					ticketDetails.setPriority(Priority.LOW);
//					ticketDetails.setDescription(bodyContent);
//					ticketDetails.setCreatedOn(messages[i].getReceivedDate());
//					ticketDetails.setTicketStatus(TicketStatus.PENDING);
//					ticketDetails.setAssociatedService(AssociatedService.SUPPORT);
//					ticketDetails.setUniqueId(uId);
//					Address address[] = messages[i].getAllRecipients();
//
//					if (address.length > 1) {
//						ticketDetails.setCustomerEmail(address[0].toString());
//						ticketDetails.setCcEmail(address[1].toString());
//					}
//
//					ticketDetails.setCustomerEmail(
//							ApplicationUtil.getRemoveAngleBraces(messages[i].getFrom()[0].toString()));
//					newEmailContent
//							.setSenderEmail(ApplicationUtil.getRemoveAngleBraces(messages[i].getFrom()[0].toString()));
//					newEmailContent.setSentOn(messages[i].getReceivedDate());
//					newEmailContent.setContent(bodyContent);
//					newEmailContent.setSubject(messages[i].getSubject());
//					newEmailContent.setMessageUniqueId(trimNewEmailUniqueId);
//					newEmailContent.setTicketDetails(ticketDetails);
//					iTicketDetailsDao.create(ticketDetails);
//					iEmailContentService.create(newEmailContent);
//					if (attachment != null) {
//						for (String emailAttachment : attachment) {
//							EmailAttachment emailAttachement = new EmailAttachment();
//							emailAttachement.setAttachment(emailAttachment);
//							emailAttachement.setEmailContent(newEmailContent);
//							emailAttachement.setDate(new Date());
//							emailAttachement.setTicketDetails(ticketDetails);
//							iEmailAttachementService.create(emailAttachement);
//						}
//
//					}
//
//				}
//			}
//			System.out.println("---------------------------------------!!!!------------------------------------");
//			folderInbox.close();
//			store.close();
//			System.out.println("-----------Connection closed-------");
//		}
//
//		catch (MessagingException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private String getBodyTextFromMessage(Message message) throws MessagingException, IOException {
//		String result = "";
//		System.out.println("message content type  :" + message.getContent().toString());
//		System.out.println("mesage check  :" + message.isMimeType("multipart/*"));
//		if (message.isMimeType("text/plain")) {
//			result = message.getContent().toString();
//		} else if (message.isMimeType("multipart/*")) {
//			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
//			result = getTextFromMimeMultipart(mimeMultipart);
//		}
//
//		return result;
//	}
//
//	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
//
//		String result = "";
//		int count = mimeMultipart.getCount();
//		for (int i = 0; i < count; i++) {
//			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
//
//			if (bodyPart.isMimeType("text/plain")) {
//				result = result + "\n" + bodyPart.getContent();
//				break;
//
//			} else if (bodyPart.isMimeType("text/html")) {
//				String html = (String) bodyPart.getContent();
//				result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
//			} else if (bodyPart.getContent() instanceof MimeMultipart) {
//				result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
//				System.out.println("multipart print   :" + result);
//			}
//		}
//		return result;
//	}
//
//	public List<String> getAttachment(Message message) throws MessagingException, IOException {
//		LocalTime time = LocalTime.now();
//		List<String> attachment = new ArrayList<String>();
//		// System.out.println(time.getNano());
//
//		Multipart multiPart = (Multipart) message.getContent();
//
//		System.out.println("multipart count       :" + multiPart.getCount());
//		for (int i = 0; i < multiPart.getCount(); i++) {
//			MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
//			if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
//				String destFilePath = "C:\\Users\\Dharmesh\\Desktop\\output\\" + "-" + time.getNano()
//						+ part.getFileName();
//
//				System.out.println("File name " + part.getFileName());
//				File file = new File(destFilePath);
//				FileOutputStream output = new FileOutputStream(file);
//				InputStream input = part.getInputStream();
//				byte[] buffer = new byte[4096];
//				int byteRead;
//				while ((byteRead = input.read(buffer)) != -1) {
//					output.write(buffer, 0, byteRead);
//				}
//				attachment.add(destFilePath);
//				output.close();
//			}
//		}
//		return attachment;
//	}
//
//	@Override
//	public TicketDetails create(TicketDetails anEntity, User user) {
//
//		EmailContent emailContent = new EmailContent();
//		emailContent.setContent(anEntity.getDescription());
//		emailContent.setSenderEmail(anEntity.getCustomerEmail());
//		emailContent.setSubject(anEntity.getSubject());
//		emailContent.setTicketDetails(anEntity);
//		anEntity.setCreatedOn(new Date());
//		anEntity.setPriority(Priority.LOW);
//		anEntity.setAssociatedService(AssociatedService.SUPPORT);
//		anEntity.setTicketStatus(TicketStatus.PENDING);
//		anEntity.setTicketId(ApplicationUtil.generateTickedId());
//		anEntity.setAddedBy(user);
//		TicketDetails ticketDetails = iTicketDetailsDao.create(anEntity);
//		iEmailContentService.create(emailContent);
//		return ticketDetails;
//	}
//
//	@Override
//	public TicketDetails update(Integer assignedTo, User user, Integer ticketDetailsId) {
//
//		TicketDetails ticketDetails = new TicketDetails();
//		TicketAssignDetails ticketAssignDetails = new TicketAssignDetails();
//		ticketAssignDetails.setAssignedOn(new Date());
//		ticketAssignDetails.setAssignedTo(iUserService.findByPk(assignedTo));
//		ticketAssignDetails.setAssignedBy(user);
//		ticketAssignDetails.setTicketDetails(iTicketDetailsDao.findByPk(ticketDetailsId));
//		TicketDetails updatedTicketDetails = create(ticketDetails);
//		ticketAssignDetails.setAssignedBy(user);
//		ticketAssignDetails.setAssignedTo(iUserService.findByPk(assignedTo));
//		ticketAssignDetails.setAssignedOn(new Date());
//				
//
//		return updatedTicketDetails;
//	}
//
//	@Override
//	public List<TicketDetails> findAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TicketDetails findByPk(Integer entityPk) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public TicketDetails create(TicketDetails anEntity) {
//		return iTicketDetailsDao.create(anEntity);
//	}
//
//	@Override
//	public TicketDetails update(TicketDetails anEntity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
