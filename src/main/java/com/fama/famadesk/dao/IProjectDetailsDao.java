package com.fama.famadesk.dao;

import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.ProjectDetails;

public interface IProjectDetailsDao extends IBaseDao<ProjectDetails> {

	List<String> getAllConfiguredProject();

	List<ProjectDetails> findByProjectName(String projectName);

	public List<ProjectDetails> getProjectByUser(Integer userid);

	List<ProjectDetails> getProjectsByAccountId(Integer accountid);

	ProjectDetails getProjectDetailByUserAndProject(Integer projectid, Integer userid);
}
