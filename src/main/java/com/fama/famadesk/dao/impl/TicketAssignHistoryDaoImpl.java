package com.fama.famadesk.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.ITicketAssignHistoryDao;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.TicketAssignHistory;
import com.fama.famadesk.repository.TicketAssignHistoryRepository;

@Component
public class TicketAssignHistoryDaoImpl implements ITicketAssignHistoryDao {
	@Autowired
	TicketAssignHistoryRepository ticketAssignHistoryRepository;

	@Override
	public TicketAssignHistory create(TicketAssignHistory anEntity) {
		return ticketAssignHistoryRepository.save(anEntity);
	}

	@Override
	public TicketAssignHistory update(TicketAssignHistory anEntity) {

		return ticketAssignHistoryRepository.save(anEntity);
	}

	@Override
	public List<TicketAssignHistory> findAll() {
		return ticketAssignHistoryRepository.findAll();
	}

	@Override
	public TicketAssignHistory findByPk(Integer entityPk) {
		return ticketAssignHistoryRepository.findById(entityPk)
				.orElseThrow(() -> new BusinessValidationException("Ticket assign details not found"));

	}

	@Override
	public List<TicketAssignHistory> getTicketAssignHistoryByTicketId(Integer ticketId) {
		List<TicketAssignHistory> ticketAssignDetails = ticketAssignHistoryRepository
				.getTicketAssignHistoryByTicketId(ticketId);

		return ticketAssignDetails;

	}

}
