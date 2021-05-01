package com.fama.famadesk.dao;

import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.TicketAssignHistory;

public interface ITicketAssignHistoryDao extends IBaseDao<TicketAssignHistory> {

	List<TicketAssignHistory> getTicketAssignHistoryByTicketId(Integer ticketId);

}
