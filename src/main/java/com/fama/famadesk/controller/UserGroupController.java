package com.fama.famadesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fama.famadesk.components.databeans.common.ApiResponse;
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.UserGroupRequest;
import com.fama.famadesk.service.IUserGroupService;

@RestController
@RequestMapping("usergroup")
public class UserGroupController extends BaseController {

	@Autowired
	private IUserGroupService userGroupService;

	@PostMapping("/addMemberToGroup")
	public ApiResponse addMemberToGroup(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestBody UserGroupRequest addMemberRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		userGroupService.addMemberToGroup(addMemberRequest, loggedInUser);
		return new ApiResponse(null, ADDED_SUCCESSFULLY);
	}

	@PutMapping("/removeMember")
	public ApiResponse removeMember(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestBody UserGroupRequest removeMemberRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		userGroupService.removeMember(removeMemberRequest, loggedInUser);
		return new ApiResponse(null, REMOVED_SUCCESSFULLY);
	}

}
