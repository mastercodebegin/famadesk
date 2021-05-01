package com.fama.famadesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.TicketAssignHistory;
import com.fama.famadesk.service.ITicketAssignHistoryService;

@RestController
@RequestMapping("ticketAssign")
public class TicketAssignHistoryController extends BaseController {

	@Autowired
	private ITicketAssignHistoryService iTicketAssignHistoryService;

	@GetMapping("/getTicketHistory/{ticketId}")
	public List<TicketAssignHistory> getTicketAssigntHistoryByTicketId(
			@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@PathVariable("ticketId") Integer ticketId) {
		getAuthorizedUser(jwtToken);
		return iTicketAssignHistoryService.getTicketAssignHistoryByTicketId(ticketId);
	}

}
