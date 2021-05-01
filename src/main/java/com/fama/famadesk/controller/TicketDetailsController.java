package com.fama.famadesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fama.famadesk.components.databeans.common.ApiResponse;
import com.fama.famadesk.constants.ResponseConstants;
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.TicketAssignHistory;
import com.fama.famadesk.model.TicketDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AssignTicketRequest;
import com.fama.famadesk.requestobject.ManualTicketCreateRequest;
import com.fama.famadesk.service.ITicketDetailsService;

@RestController
@RequestMapping("/ticket")
public class TicketDetailsController extends BaseController {
	@Autowired
	ITicketDetailsService iTicketDetailsService;

	@PostMapping("/manuallyCreateTicket")
	public TicketDetails manuallyCreateTicket(@RequestParam(required = true) String jwtToken,
			@RequestBody ManualTicketCreateRequest manualTicketCreateRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		return iTicketDetailsService.manuallyCreateTicket(manualTicketCreateRequest, loggedInUser);
	}

	@PutMapping("/updateTicket")
	public ApiResponse updateTicket(@RequestParam(required = true) String jwtToken, @RequestBody TicketDetails anEntity,
			@RequestParam(required = true) Integer ticketId) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		TicketDetails ticketDetails = iTicketDetailsService.update(anEntity, ticketId, null, loggedInUser);
		return new ApiResponse(ticketDetails, ResponseConstants.SUCCESS_MSG, ResponseConstants.SUCCESS_RESPONSE);

	}

	@PostMapping("/assignticket")
	public ApiResponse assignticket(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestBody AssignTicketRequest assignTicketRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		TicketAssignHistory ticketAssignDetails = iTicketDetailsService
				.assignTicketAndMaintainHistory(assignTicketRequest, loggedInUser);
		return new ApiResponse(ticketAssignDetails, ResponseConstants.SUCCESS_MSG, ResponseConstants.SUCCESS_RESPONSE);

	}

	@PostMapping("/forwardTicketRequest")
	public ApiResponse forwardTicketRequest(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestBody AssignTicketRequest assignTicketRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		TicketAssignHistory ticketAssignDetails = iTicketDetailsService.forwardTicketRequest(assignTicketRequest,
				loggedInUser);
		return new ApiResponse(ticketAssignDetails, ResponseConstants.SUCCESS_MSG, ResponseConstants.SUCCESS_RESPONSE);

	}

	@PutMapping("/markTicketRequestAsCompleted")
	public ApiResponse markTicketRequestAsCompleted(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestParam(name = "ticketDetailId") Integer ticketDetailId) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		iTicketDetailsService.markTicketRequestAsCompleted(ticketDetailId, loggedInUser);
		return new ApiResponse(null, ResponseConstants.SUCCESS_MSG, ResponseConstants.SUCCESS_RESPONSE);

	}

}
