package com.fama.famadesk.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.components.helper.EncryptionHelper;
import com.fama.famadesk.components.rolevalidator.IRoleValidator;
import com.fama.famadesk.components.util.ApplicationUtil;
import com.fama.famadesk.dao.IProjectDetailsDao;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.GroupDetails;
import com.fama.famadesk.model.ProjectDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AddProjectDetailRequest;
import com.fama.famadesk.service.IGroupDetailsService;
import com.fama.famadesk.service.IProjectDetailsService;

@Service
public class ProjectDetailsServiceImpl implements IProjectDetailsService {
	@Autowired
	private IProjectDetailsDao projectDetailsDao;

	@Autowired
	private IGroupDetailsService groupDetailsService;

	@Autowired
	private EncryptionHelper encryptionHelper;

	@Autowired
	private IRoleValidator roleValidator;

	@Override
	public ProjectDetails create(ProjectDetails anEntity) {
		return projectDetailsDao.create(anEntity);
	}

	@Override
	public ProjectDetails update(ProjectDetails anEntity) {
		return projectDetailsDao.update(anEntity);
	}

	@Override
	public List<ProjectDetails> findAll() {

		return projectDetailsDao.findAll();
	}

	@Override
	public ProjectDetails findByPk(Integer entityPk) {

		return projectDetailsDao.findByPk(entityPk);
	}

	@Override
	public void addProject(AddProjectDetailRequest addProjectDetailRequest, User loggedInUser) {
		if (!roleValidator.checkIfAccessValidationRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}

		if ((addProjectDetailRequest.getProjectName() == null
				|| addProjectDetailRequest.getProjectName().trim().isEmpty())
				&& (addProjectDetailRequest.getOfficialEmail() == null
						|| addProjectDetailRequest.getOfficialEmail().trim().isEmpty())
				&& (addProjectDetailRequest.getPassword() == null
						|| addProjectDetailRequest.getPassword().trim().isEmpty())) {
			throw new BusinessValidationException("Invalid request, please check the entered value");
		}

		// validating email
		ApplicationUtil.checkEmail(addProjectDetailRequest.getOfficialEmail());

		// to do- check to restrict same project name
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setProjectName(addProjectDetailRequest.getProjectName());
		projectDetails.setStartDate(new Date());
		projectDetails.setOfficialEmail(addProjectDetailRequest.getOfficialEmail());
		projectDetails.setPassword(encryptionHelper.encryptData(addProjectDetailRequest.getPassword()));
		projectDetails.setAddedBy(loggedInUser);
		create(projectDetails);
	}

	@Override
	public List<String> getAllConfiguredProject() {
		return projectDetailsDao.getAllConfiguredProject();
	}

	@Override
	public List<ProjectDetails> findByProjectName(String projectName) {
		return projectDetailsDao.findByProjectName(projectName);
	}

	@Override
	public List<ProjectDetails> getProjectByUser(User loggedInUser) {
		return projectDetailsDao.getProjectByUser(loggedInUser.getUserid());
	}

	@Override
	public List<ProjectDetails> getProjectsByAccountId(Integer accountid, User loggedInUser) {
		return projectDetailsDao.getProjectsByAccountId(accountid);
	}

	@Override
	public void removeProject(Integer projectid, User loggedInUser) {
		if (!roleValidator.ValidateAccAdminRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}
		ProjectDetails projectDetails = projectDetailsDao.getProjectDetailByUserAndProject(projectid,
				loggedInUser.getUserid());
		projectDetails.setEndDate(new Date());
		projectDetails.setUpdatedBy(loggedInUser);
		update(projectDetails);

		removeGroup(projectid, loggedInUser);

	}

	private void removeGroup(Integer projectid, User loggedInUser) {
		List<GroupDetails> groupDetailsList = groupDetailsService.getGroupByProjectId(projectid, loggedInUser);
		for (GroupDetails groupDetails : groupDetailsList) {
			groupDetailsService.removeGroup(groupDetails.getId(), loggedInUser);
		}
	}

}
