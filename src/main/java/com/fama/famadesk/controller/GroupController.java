package com.fama.famadesk.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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
import com.fama.famadesk.requestobject.AddGroupRequest;
import com.fama.famadesk.service.IGroupDetailsService;

@RestController
@RequestMapping("group")
public class GroupController extends BaseController {

	@Autowired
	private IGroupDetailsService groupDetailsService;

	@PostMapping("/addGroup")
	public ApiResponse addGroup(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@Valid @NotEmpty @RequestBody AddGroupRequest addGroupRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		groupDetailsService.addGroup(addGroupRequest, loggedInUser);
		return new ApiResponse(null, SUCCESS_MSG);
	}

	@PutMapping("/removeGroup")
	public ApiResponse removeGroup(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestParam(name = "groupid") Integer groupid) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		groupDetailsService.removeGroup(groupid, loggedInUser);
		return new ApiResponse(null, SUCCESS_MSG);
	}
}
