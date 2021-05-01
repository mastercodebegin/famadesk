package com.fama.famadesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.dao.ITicketAssignHistoryDao;
import com.fama.famadesk.model.TicketAssignHistory;
import com.fama.famadesk.service.ITicketAssignHistoryService;
import com.fama.famadesk.service.ITicketDetailsService;
import com.fama.famadesk.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketAssignHistoryServiceImpl implements ITicketAssignHistoryService {

	@Autowired
	ITicketAssignHistoryDao iTicketAssignHistoryDao;
	@Autowired
	IUserService iUserService;
	@Autowired
	ITicketDetailsService iTicketDetailsService;

	@Override
	public TicketAssignHistory create(TicketAssignHistory anEntity) {
		return iTicketAssignHistoryDao.create(anEntity);
	}

	@Override
	public TicketAssignHistory update(TicketAssignHistory anEntity) {

		return iTicketAssignHistoryDao.update(anEntity);
	}

	@Override
	public List<TicketAssignHistory> findAll() {
		return iTicketAssignHistoryDao.findAll();
	}

	@Override
	public TicketAssignHistory findByPk(Integer entityPk) {
		return iTicketAssignHistoryDao.findByPk(entityPk);

	}

	@Override
	public List<TicketAssignHistory> getTicketAssignHistoryByTicketId(Integer ticketId) {

		return iTicketAssignHistoryDao.getTicketAssignHistoryByTicketId(ticketId);
	}

}
