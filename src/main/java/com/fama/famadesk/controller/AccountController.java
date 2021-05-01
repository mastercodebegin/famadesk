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
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AccountSetupRequest;
import com.fama.famadesk.service.IAccountService;

@RestController
@RequestMapping("account")
public class AccountController extends BaseController {

	@Autowired
	private IAccountService accountService;

	@PostMapping("/setupAccount")
	public ApiResponse setupAccount(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestBody AccountSetupRequest accountSetupRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		accountService.setupAccount(accountSetupRequest, loggedInUser);
		return new ApiResponse(null, SUCCESS_MSG);
	}

	@PutMapping("/removeAccount")
	public ApiResponse removeAccount(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestParam(name = "accountid") Integer accountid) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		accountService.removeAccount(accountid, loggedInUser);
		return new ApiResponse(null, SUCCESS_MSG);
	}

}
