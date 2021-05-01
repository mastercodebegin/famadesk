package com.fama.famadesk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fama.famadesk.components.databeans.common.ApiResponse;
import com.fama.famadesk.constants.ResponseConstants;
import com.fama.famadesk.controller.basecontroller.BaseController;
import com.fama.famadesk.model.Roles;
import com.fama.famadesk.service.IRoleService;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController {

	@Autowired
	IRoleService roleService;

	@GetMapping("/getAllAvailablesRoles")
	public ApiResponse getAllAvailablesRoles() {
		List<Roles> roleList = roleService.getAvailableRoles();
		return new ApiResponse(roleList, ResponseConstants.SUCCESS_MSG);

	}

}
