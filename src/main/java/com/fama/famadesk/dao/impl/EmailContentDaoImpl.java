package com.fama.famadesk.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.IEmailAttachementDao;
import com.fama.famadesk.dao.IEmailContentDao;
import com.fama.famadesk.model.EmailAttachment;
import com.fama.famadesk.model.EmailContent;
import com.fama.famadesk.repository.EmailAttachementRepository;
import com.fama.famadesk.repository.EmailContentRepository;

@Component
public class EmailContentDaoImpl implements IEmailContentDao {
	@Autowired
	EmailContentRepository emailContentRepository;

	@Override
	public EmailContent create(EmailContent anEntity) {
		return emailContentRepository.save(anEntity);
	}

	@Override
	public EmailContent update(EmailContent anEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailContent findByPk(Integer entityPk) {
		return emailContentRepository.findByEmailContentId(entityPk);
	}

	@Override
	public List<EmailContent> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailContent> getEmailContentByMessageUniqueId(String emailUniqueId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailContent getNewestEmailContentByEmailUniqueId(String emailUniqueId) {
		return emailContentRepository.getNewestEmailContentByEmailUniqueId(emailUniqueId);
	}

}
