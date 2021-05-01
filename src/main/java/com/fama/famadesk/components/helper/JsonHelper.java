package com.fama.famadesk.components.helper;

import org.springframework.stereotype.Component;

import com.fama.famadesk.model.EmailContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class JsonHelper {
public  EmailContent getJson(String emailContentData)
{
	
EmailContent emailContent = new EmailContent();	
ObjectMapper objectMapper= new ObjectMapper();
try {
	emailContent=objectMapper.readValue(emailContentData, EmailContent.class);
} catch (JsonMappingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (JsonProcessingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
System.out.println("emailmaper------"+emailContent);
return emailContent;
}
}
