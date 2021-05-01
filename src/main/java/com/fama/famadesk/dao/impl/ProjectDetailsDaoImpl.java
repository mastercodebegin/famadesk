package com.fama.famadesk.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.IProjectDetailsDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.ProjectDetails;
import com.fama.famadesk.repository.ProjectDetailsRepository;

@Component
public class ProjectDetailsDaoImpl implements IProjectDetailsDao {

	@Autowired
	private ProjectDetailsRepository projectDetailsRepository;

	@Override
	public ProjectDetails create(ProjectDetails anEntity) {

		return projectDetailsRepository.save(anEntity);
	}

	@Override
	public ProjectDetails update(ProjectDetails anEntity) {

		return projectDetailsRepository.save(anEntity);
	}

	@Override
	public ProjectDetails findByPk(Integer entityPk) {
		return projectDetailsRepository.findById(entityPk)
				.orElseThrow(() -> new DataNotFoundException("Project details not found"));
	}

	@Override
	public List<ProjectDetails> findAll() {

		return projectDetailsRepository.findAll();
	}

	@Override
	public List<String> getAllConfiguredProject() {
		return projectDetailsRepository.getAllConfiguredProject().orElse(Collections.emptyList());
	}

	@Override
	public List<ProjectDetails> findByProjectName(String projectName) {
		return projectDetailsRepository.findByProjectName(projectName).orElse(Collections.emptyList());
	}

	@Override
	public List<ProjectDetails> getProjectByUser(Integer userid) {
		return projectDetailsRepository.getProjectByUser(userid).orElse(Collections.emptyList());
	}

	@Override
	public List<ProjectDetails> getProjectsByAccountId(Integer accountid) {
		return projectDetailsRepository.getProjectsByAccountId(accountid).orElse(Collections.emptyList());

	}

	@Override
	public ProjectDetails getProjectDetailByUserAndProject(Integer projectid, Integer userid) {
		return projectDetailsRepository.getProjectDetailByUserAndProject(projectid, userid)
				.orElseThrow(() -> new DataNotFoundException("Project details not found"));
	}

}
