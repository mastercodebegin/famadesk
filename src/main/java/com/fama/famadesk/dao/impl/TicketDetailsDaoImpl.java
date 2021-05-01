package com.fama.famadesk.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.ITicketDetailsDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.TicketDetails;
import com.fama.famadesk.repository.TicketDetailsRepository;

@Component
public class TicketDetailsDaoImpl implements ITicketDetailsDao {

	@Autowired
	TicketDetailsRepository ticketDetailsRepository;

	@Override
	public TicketDetails create(TicketDetails anEntity) {
		return ticketDetailsRepository.save(anEntity);
	}

	@Override
	public TicketDetails update(TicketDetails anEntity) {
		return ticketDetailsRepository.save(anEntity);
	}

	@Override
	public TicketDetails findByPk(Integer entityPk) {
		return ticketDetailsRepository.findById(entityPk)
				.orElseThrow(() -> new DataNotFoundException("Ticket details not found"));

	}

	@Override
	public List<TicketDetails> findAll() {
		return ticketDetailsRepository.findAll();
	}

	@Override
	public TicketDetails findByticketId(String ticketId) {
		return ticketDetailsRepository.findByticketId(ticketId);
	}

	@Override
	public List<TicketDetails> getPendingTicketByAssignee(Integer assignedTo) {
		return ticketDetailsRepository.getPendingTicketByAssignee(assignedTo).orElse(Collections.emptyList());
	}

	@Override
	public long getPendingTicketCount() {
		return ticketDetailsRepository.getPendingTicketCount();
	}

	@Override
	public long getPendingTicketCountByAssignee(Integer assigneeId) {
		return ticketDetailsRepository.getPendingTicketCountByAssignee(assigneeId);
	}

}
