package com.fama.famadesk.service;

import java.util.List;

import javax.mail.Multipart;

import org.springframework.web.multipart.MultipartFile;

import com.fama.famadesk.model.EmailContent;
import com.fama.famadesk.model.User;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface IEmailContentService extends IBaseService<EmailContent> {
	public List<EmailContent> getEmailContentByMessageUniqueId(String emailUniqueId);
	
	public EmailContent getNewestEmailContentByEmailUniqueId(String emailUniqueId);

	EmailContent sendReplyEmail(Integer emailContentId,Integer ticketDetailsId,User loggedInUser, EmailContent replyEmailContent,MultipartFile []files);

	

	

	

}
