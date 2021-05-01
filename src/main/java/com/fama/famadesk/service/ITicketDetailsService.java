package com.fama.famadesk.service;

import java.util.List;

import com.fama.famadesk.model.TicketAssignHistory;
import com.fama.famadesk.model.TicketDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AssignTicketRequest;
import com.fama.famadesk.requestobject.ManualTicketCreateRequest;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface ITicketDetailsService extends IBaseService<TicketDetails> {
	TicketDetails manuallyCreateTicket(ManualTicketCreateRequest manualTicketCreateRequest, User loggedInUser);

	TicketDetails update(TicketDetails anEntity, Integer ticketId, Integer assignTo, User loggedInUser);

	TicketDetails findByticketId(String ticketId);

	TicketAssignHistory assignTicketAndMaintainHistory(AssignTicketRequest assignTicketRequest, User loggedInUser);

	TicketAssignHistory forwardTicketRequest(AssignTicketRequest assignTicketRequest, User loggedInUser);

	void markTicketRequestAsCompleted(Integer ticketDetailId, User loggedInUser);

	List<TicketDetails> getPendingTicketByAssignee(Integer assignedTo);

	long getPendingTicketCount(User loggedInUser);

}
