package com.fama.famadesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.dao.IEmailAttachementDao;
import com.fama.famadesk.model.EmailAttachment;
import com.fama.famadesk.service.IEmailAttachmentService;

@Service
public class EmailAttachementServiceImpl implements IEmailAttachmentService {
	@Autowired
	IEmailAttachementDao iEmailAttachementDao;

	@Override
	public EmailAttachment create(EmailAttachment anEntity) {
		return iEmailAttachementDao.create(anEntity);

	}

	@Override
	public EmailAttachment update(EmailAttachment anEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailAttachment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmailAttachment findByPk(Integer entityPk) {
		// TODO Auto-generated method stub
		return null;
	}

}
