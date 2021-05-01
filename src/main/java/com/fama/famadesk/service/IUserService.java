package com.fama.famadesk.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.Login;
import com.fama.famadesk.requestobject.UserCreationRequest;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface IUserService extends IBaseService<User> {
	public User findByEmail(String email);

	public String login(Login login, HttpServletRequest request, HttpServletResponse response);

	public void createNewUser(UserCreationRequest userCreationRequest, User loggedInUser);

	public List<User> getVerifiedActiveAgent(User loggedInUser);

}
