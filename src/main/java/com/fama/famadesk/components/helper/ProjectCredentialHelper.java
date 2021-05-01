package com.fama.famadesk.components.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.model.ProjectDetails;
import com.fama.famadesk.service.IProjectDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProjectCredentialHelper {

	@Autowired
	private IProjectDetailsService projectDetailsService;

	public static Map<String, List<ProjectDetails>> projectCredentialMap = new HashMap<String, List<ProjectDetails>>();

	// @Scheduled(fixedDelay = 1000)
	public void getProjectDetailsCredential() {
		// System.out.println("hi");
		List<String> projectNameList = projectDetailsService.getAllConfiguredProject();

		for (String projectName : projectNameList) {
			// Map<String, List<ProjectDetails>> map = new HashMap<String,
			// List<ProjectDetails>>();

			List<ProjectDetails> projectList = projectDetailsService.findByProjectName(projectName);

			List<ProjectDetails> list = projectCredentialMap.get(projectName);

			if (list == null || list.isEmpty()) {
				projectCredentialMap.put(projectName, projectList);
			}
		}
	}

}
