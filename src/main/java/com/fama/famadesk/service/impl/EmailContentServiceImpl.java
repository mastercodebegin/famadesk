package com.fama.famadesk.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fama.famadesk.dao.IEmailContentDao;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.EmailAttachment;
import com.fama.famadesk.model.EmailContent;
import com.fama.famadesk.model.TicketDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.service.IEmailAttachmentService;
import com.fama.famadesk.service.IEmailContentService;
import com.fama.famadesk.service.ITicketDetailsService;
import com.fama.famadesk.service.IUserService;
import com.google.common.io.Files;
import com.sun.istack.ByteArrayDataSource;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.smtp.SMTPTransport;

@Service
@SuppressWarnings("unused")
public class EmailContentServiceImpl implements IEmailContentService {
	@Autowired
	IEmailContentDao iEmailContentDao;
	@Autowired
	ITicketDetailsService iTicketDetailsService;
	@Autowired
	IEmailContentService iEmailContentService;
	@Autowired
	IUserService iUserService;
	@Autowired
	IEmailAttachmentService iEmailAttachmentService;

	@Override
	public EmailContent create(EmailContent anEntity) {
		return iEmailContentDao.create(anEntity);
	}

	@Override
	public EmailContent update(EmailContent anEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailContent> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailContent findByPk(Integer entityPk) {
		return iEmailContentDao.findByPk(entityPk);
	}

	@Override
	public List<EmailContent> getEmailContentByMessageUniqueId(String emailUniqueId) {
		return iEmailContentDao.getEmailContentByMessageUniqueId(emailUniqueId);
	}

	@Override
	public EmailContent getNewestEmailContentByEmailUniqueId(String emailUniqueId) {
		return iEmailContentDao.getNewestEmailContentByEmailUniqueId(emailUniqueId);
	}

	@Override
	public EmailContent sendReplyEmail(Integer emailContentId, Integer ticketDetailsId, User loggedInUser,
			EmailContent replyEmailContent, MultipartFile files[]) {

		String SMTP_Server = "imap-mail.outlook.com";
		String username = "famadesk@outlook.com";
		String password = "Fama@123";
		String emailFrom = "famadesk@outlook.com";
		String emailTo = "";
		String emailToCc = "";
		String emailSubject = replyEmailContent.getSubject();
		String emailText = replyEmailContent.getContent();

		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", SMTP_Server); // optional, defined in SMTPTransport
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "25"); // default port 25
		prop.put("mail.smtp.starttls.enable", "true");
		Message message = null;
		EmailContent fetchedEmailContentDetails = null;

		Session mailSession = Session.getInstance(prop, new Authenticator() {

			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}

		});

		Properties properties = new Properties();
		properties.put("mail.imap.host", SMTP_Server);
		properties.put("mail.imap.port", 993);
		properties.setProperty("mail.imap.ssl.enable", "true");
		Session session = Session.getDefaultInstance(properties);
		try {
			Store store = session.getStore("imap");
			store.connect(SMTP_Server, username, password);

			IMAPFolder folderInbox = (IMAPFolder) store.getFolder("INBOX");
			folderInbox.open(Folder.READ_WRITE);

//get unread mail
			TicketDetails ticketTidetails = iTicketDetailsService.findByPk(ticketDetailsId);
			if (ticketTidetails == null) {
				throw new BusinessValidationException("Ticket details not found");
			}
			fetchedEmailContentDetails = findByPk(emailContentId);
			if (fetchedEmailContentDetails == null) {
				throw new BusinessValidationException("Email content details not found");
			}
			System.out.println("email object   :" + fetchedEmailContentDetails);
			System.out.println("Unique Id" + fetchedEmailContentDetails.getTicketDetails().getUniqueId());
			message = folderInbox.getMessageByUID(fetchedEmailContentDetails.getTicketDetails().getUniqueId());
			emailTo = fetchedEmailContentDetails.getSenderEmail();
			emailToCc = fetchedEmailContentDetails.getTicketDetails().getCcEmail();
		} catch (MessagingException m) {

		}

		Message msg = new MimeMessage(mailSession);
		EmailContent emailContent = new EmailContent();
		EmailContent emailContentResponse = new EmailContent();
		List<EmailAttachment> emailAttachementObjectList = new ArrayList<EmailAttachment>();
		try {
			msg = message.reply(false);
			msg.setFrom(new InternetAddress(emailFrom));
			msg.setText(replyEmailContent.getContent());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false));
			if (emailToCc != null) {
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailToCc, false));
			}
			msg.setSubject(fetchedEmailContentDetails.getSubject());
			msg.setSentDate(new Date());
			// msg.setSubject(fetchedEmailContentDetails.getSubject());
			msg.setSubject("Re: " + fetchedEmailContentDetails.getSubject());

			if (files != null) {
				File uploadedFilePath = null;
				MimeMultipart multipart = new MimeMultipart();
				MimeBodyPart messageTextBodyPart = new MimeBodyPart();
				messageTextBodyPart.setContent(replyEmailContent.getContent(), "text/html; charset=utf-8");

				for (MultipartFile file : files) {
					EmailAttachment emailAttachment = new EmailAttachment();
					uploadedFilePath = new File("C:\\Users\\Dharmesh\\Desktop\\output\\" + file.getOriginalFilename());
					emailAttachment.setAttachment(uploadedFilePath.toString());
					emailAttachment.setDate(new Date());
					emailAttachment.setTicketDetails(replyEmailContent.getTicketDetails());
					emailAttachementObjectList.add(emailAttachment);
					try {
						Files.write(file.getBytes(), uploadedFilePath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					try {
						BodyPart messageMultiPartBodyPart = new MimeBodyPart();
						InputStream imageStream = new FileInputStream(uploadedFilePath);
						DataSource fileDataStream = new ByteArrayDataSource(IOUtils.toByteArray(imageStream),
								"image/gif");
						messageMultiPartBodyPart.setDataHandler(new DataHandler(fileDataStream));
						messageMultiPartBodyPart.setFileName(uploadedFilePath.getName());
						multipart.addBodyPart(messageMultiPartBodyPart);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				multipart.addBodyPart(messageTextBodyPart);
				msg.setContent(multipart);

			}

			SMTPTransport transport = (SMTPTransport) mailSession.getTransport("smtp");
			transport.connect(SMTP_Server, username, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

			System.out.println("E-mail Sent Successfully");
			// User loggedInUser=iUserService.findByPk(1);
			// System.out.println("User obj-----------"+loggedInUser);
			emailContent.setContent(replyEmailContent.getContent());
			emailContent.setMessageUniqueId(fetchedEmailContentDetails.getMessageUniqueId());
			emailContent.setSenderEmail(fetchedEmailContentDetails.getSenderEmail());
			emailContent.setSentOn(new Date());
			emailContent.setSubject(fetchedEmailContentDetails.getSubject());
			emailContent.setReplyBy(loggedInUser);
			emailContent.setTicketDetails(fetchedEmailContentDetails.getTicketDetails());
			emailContentResponse = iEmailContentService.create(emailContent);

		}

		catch (MessagingException e) {
			e.printStackTrace();
		}

		if (files != null) {
			for (EmailAttachment emailAttachement : emailAttachementObjectList) {
				System.out.println("for lopp");
				emailAttachement.setEmailContent(emailContent);
				iEmailAttachmentService.create(emailAttachement);
				System.out.println("-------Attachement saved successfully------");
			}
		}
		System.out.println("-------emailcontent saved successfully------");

		return emailContentResponse;

	}

}
