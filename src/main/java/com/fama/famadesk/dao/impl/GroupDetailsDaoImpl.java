package com.fama.famadesk.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.IGroupDetailsDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.GroupDetails;
import com.fama.famadesk.repository.GroupDetailsRepository;

@Component
public class GroupDetailsDaoImpl implements IGroupDetailsDao {
	@Autowired
	private GroupDetailsRepository groupDetailsRepository;

	@Override
	public GroupDetails create(GroupDetails anEntity) {
		return groupDetailsRepository.save(anEntity);
	}

	@Override
	public GroupDetails update(GroupDetails anEntity) {
		return groupDetailsRepository.save(anEntity);
	}

	@Override
	public GroupDetails findByPk(Integer entityPk) {
		return groupDetailsRepository.findById(entityPk)
				.orElseThrow(() -> new DataNotFoundException("Group details not found"));
	}

	@Override
	public List<GroupDetails> findAll() {
		return groupDetailsRepository.findAll();
	}

	@Override
	public List<GroupDetails> getGroupByProjectId(Integer projectid) {
		return groupDetailsRepository.getGroupByProjectId(projectid).orElse(Collections.emptyList());
	}

	@Override
	public GroupDetails getDetailsByGroupAndAccountAdmin(Integer groupid, Integer accountAdminId) {
		return groupDetailsRepository.getDetailsByGroupAndAccountAdmin(groupid, accountAdminId)
				.orElseThrow(() -> new DataNotFoundException("Group details not found"));
	}

}
