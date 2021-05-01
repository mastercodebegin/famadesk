package com.fama.famadesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fama.famadesk.components.databeans.common.ApiResponse;
import com.fama.famadesk.constants.ResponseConstants;
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.UserCreationRequest;
import com.fama.famadesk.service.IUserService;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {
	@Autowired
	IUserService iUserService;

	@PostMapping("/createUser")
	public ApiResponse createNewUser(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestBody UserCreationRequest userCreationRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);

		iUserService.createNewUser(userCreationRequest, loggedInUser);
		return new ApiResponse(null, ResponseConstants.USER_CREATED_SUCCESSFULLY, ResponseConstants.SUCCESS_RESPONSE);
	}

	@GetMapping("/getVerifiedActiveAgent")
	public ApiResponse getVerifiedActiveAgent(@RequestHeader(name = "jwtToken", required = true) String jwtToken) {
		User loggedInUser = getAuthorizedUser(jwtToken);

		return new ApiResponse(iUserService.getVerifiedActiveAgent(loggedInUser), ResponseConstants.SUCCESS_MSG);
	}

}
