package com.fama.famadesk.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.IUserGroupDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.UserGroup;
import com.fama.famadesk.repository.UserGroupRepository;

@Component
public class UserGroupDaoImpl implements IUserGroupDao {

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Override
	public UserGroup create(UserGroup anEntity) {

		return userGroupRepository.save(anEntity);
	}

	@Override
	public UserGroup update(UserGroup anEntity) {

		return userGroupRepository.save(anEntity);
	}

	@Override
	public UserGroup findByPk(Integer entityPk) {

		return userGroupRepository.findById(entityPk)
				.orElseThrow(() -> new DataNotFoundException("User group details not found"));
	}

	@Override
	public List<UserGroup> findAll() {
		return userGroupRepository.findAll();
	}

	@Override
	public UserGroup getDetailsByGroupAndUser(Integer groupid, Integer userid) {
		return userGroupRepository.getDetailsByGroupAndUser(groupid, userid);

	}

	@Override
	public List<UserGroup> getUserGroupDetailByGroupid(Integer groupid) {
		return userGroupRepository.getDetailsByGroupid(groupid).orElse(Collections.emptyList());
	}

}
