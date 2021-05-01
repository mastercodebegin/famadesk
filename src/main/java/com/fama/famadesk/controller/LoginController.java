package com.fama.famadesk.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fama.famadesk.components.databeans.common.ApiResponse;
import com.fama.famadesk.constants.ResponseConstants;
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.requestobject.Login;
import com.fama.famadesk.service.IUserService;

@RestController
@RequestMapping("login")
public class LoginController extends BaseController {

	@Autowired
	private IUserService userService;

	@PutMapping("/login")
	public ApiResponse login(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
		userService.login(login, request, response);
		return new ApiResponse(null, ResponseConstants.SUCCESS_MSG);
	}

	@PutMapping("/logout")
	public ApiResponse logout(@RequestHeader(name = "jwtToken", required = true) String jwtToken) {
		getAuthorizedUser(jwtToken);
		// userService.eraseDeviceAndNotificationId(loggedInUser);
		return new ApiResponse("Success", ResponseConstants.SUCCESS_MSG, ResponseConstants.SUCCESS_RESPONSE);
	}

}
