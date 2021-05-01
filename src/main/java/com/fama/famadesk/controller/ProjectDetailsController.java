package com.fama.famadesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.fama.famadesk.requestobject.AddProjectDetailRequest;
import com.fama.famadesk.service.IProjectDetailsService;

@RestController
@RequestMapping("project")
public class ProjectDetailsController extends BaseController {

	@Autowired
	private IProjectDetailsService projectDetailsService;

	@PostMapping("/addProject")
	public ApiResponse addProject(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestBody AddProjectDetailRequest addProjectDetailRequest) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		projectDetailsService.addProject(addProjectDetailRequest, loggedInUser);
		return new ApiResponse(null, SUCCESS_MSG);
	}

	@GetMapping("/getProjectByUser")
	public ApiResponse getProjectByUser(@RequestHeader(name = "jwtToken", required = true) String jwtToken) {
		User loggedInUser = getAuthorizedUser(jwtToken);

		return new ApiResponse(projectDetailsService.getProjectByUser(loggedInUser), SUCCESS_MSG);
	}

	@PutMapping("/removeProject")
	public ApiResponse removeProject(@RequestHeader(name = "jwtToken", required = true) String jwtToken,
			@RequestParam(name = "projectid") Integer projectid) {
		User loggedInUser = getAuthorizedUser(jwtToken);
		projectDetailsService.removeProject(projectid, loggedInUser);
		return new ApiResponse(null, SUCCESS_MSG);
	}

}
