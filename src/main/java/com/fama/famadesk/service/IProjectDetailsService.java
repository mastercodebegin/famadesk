package com.fama.famadesk.service;

import java.util.List;

import com.fama.famadesk.model.ProjectDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AddProjectDetailRequest;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface IProjectDetailsService extends IBaseService<ProjectDetails> {

	void addProject(AddProjectDetailRequest addProjectDetailRequest, User loggedInUser);

	List<String> getAllConfiguredProject();

	List<ProjectDetails> findByProjectName(String projectName);

	List<ProjectDetails> getProjectByUser(User loggedInUser);

	List<ProjectDetails> getProjectsByAccountId(Integer accountid, User loggedInUser);

	void removeProject(Integer projectid, User loggedInUser);

}
