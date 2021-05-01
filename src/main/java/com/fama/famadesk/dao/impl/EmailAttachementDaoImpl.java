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
public class EmailAttachementDaoImpl implements IEmailAttachementDao {
	@Autowired
	EmailAttachementRepository emailAttachementRepository;

	@Override
	public EmailAttachment create(EmailAttachment anEntity) {
		return emailAttachementRepository.save(anEntity);
		
	}

	@Override
	public EmailAttachment update(EmailAttachment anEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailAttachment findByPk(Integer entityPk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailAttachment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
