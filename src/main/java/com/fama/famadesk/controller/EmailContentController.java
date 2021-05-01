package com.fama.famadesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fama.famadesk.components.helper.JsonHelper;
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.EmailContent;
import com.fama.famadesk.model.User;
import com.fama.famadesk.service.IEmailContentService;

@RestController
@RequestMapping("/emailcontent")
public class EmailContentController extends BaseController {
	@Autowired
	IEmailContentService iEmailContentService;
	@Autowired
	JsonHelper jsonHelper;

	@RequestMapping(value = "/replyemail",  method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE } )
	public EmailContent create(@RequestParam(required=true) Integer emailContentId ,
			@RequestParam(required=true) String jwtToken ,
			@RequestParam(required=true) Integer ticketDetailsId ,
			@RequestPart("emailcontent") String emailContentText,
			@RequestPart(value = "files", required = false) MultipartFile[] multipart) {
		EmailContent emailContent = new EmailContent();
		emailContent = jsonHelper.getJson(emailContentText);
		User loggedInUser = getAuthorizedUser(jwtToken);
		return iEmailContentService.sendReplyEmail( emailContentId,ticketDetailsId,loggedInUser, emailContent, multipart);

	}
@GetMapping("/getmailcontentById")
public EmailContent getEmailContentById(@RequestParam(required=true) Integer entityPk)
{
return iEmailContentService.findByPk(entityPk);
	
}
}
