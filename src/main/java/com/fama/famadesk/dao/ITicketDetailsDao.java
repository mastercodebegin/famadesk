package com.fama.famadesk.dao;

import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.TicketDetails;

public interface ITicketDetailsDao extends IBaseDao<TicketDetails> {

	TicketDetails findByticketId(String ticketId);

	List<TicketDetails> getPendingTicketByAssignee(Integer assignedTo);

	long getPendingTicketCount();

	long getPendingTicketCountByAssignee(Integer assigneeId);

}
