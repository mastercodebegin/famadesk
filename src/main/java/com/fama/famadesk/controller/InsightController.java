package com.fama.famadesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fama.famadesk.components.databeans.common.ApiResponse;
import com.fama.famadesk.constants.ResponseConstants;
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.User;
import com.fama.famadesk.service.ITicketDetailsService;

@RestController
@RequestMapping("insight")
public class InsightController extends BaseController {

	@Autowired
	private ITicketDetailsService ticketDetailsService;

	@GetMapping("/getPendingTicketCount")
	public ApiResponse getPendingTicketCount(@RequestHeader(name = "jwtToken", required = true) String jwtToken) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		ticketDetailsService.getPendingTicketCount(loggedInUser);
		return new ApiResponse(ticketDetailsService.getPendingTicketCount(loggedInUser), ResponseConstants.SUCCESS_MSG);
	}

}
