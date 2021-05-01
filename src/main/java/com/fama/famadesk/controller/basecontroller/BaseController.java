package com.fama.famadesk.controller.basecontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.components.helper.JwtHelper;
import com.fama.famadesk.constants.ResponseConstants;
import com.fama.famadesk.model.User;
import com.fama.famadesk.service.IUserService;

@Component
public class BaseController implements ResponseConstants {

	@Autowired
	private IUserService userService;

	public User getUserDetails(String email) {
		return userService.findByEmail(email);
	}

	public User getAuthorizedUser(String jwtToken) {
		return getUserDetails(getUserEmail(jwtToken));
	}

	public String getUserEmail(String jwtToken) {
		return JwtHelper.getEmail(jwtToken);
	}

	public String getUserRole(String jwtToken) {
		return JwtHelper.getUserRole(jwtToken);
	}

	public String getUserId(String jwtToken) {
		return JwtHelper.getUserId(jwtToken);
	}

}
