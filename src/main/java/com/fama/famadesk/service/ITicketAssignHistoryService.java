package com.fama.famadesk.service;

import java.util.List;

import com.fama.famadesk.model.TicketAssignHistory;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface ITicketAssignHistoryService extends IBaseService<TicketAssignHistory> {

	List<TicketAssignHistory> getTicketAssignHistoryByTicketId(Integer ticketId);

}
