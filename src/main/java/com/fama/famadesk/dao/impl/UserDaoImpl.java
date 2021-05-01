package com.fama.famadesk.dao.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.IUserDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.User;
import com.fama.famadesk.repository.UserRepository;

@Component
public class UserDaoImpl implements IUserDao {

	@Autowired
	UserRepository userRepository;

	@Override
	public User create(User anEntity) {
		return userRepository.save(anEntity);
	}

	@Override
	public User update(User anEntity) {
		return userRepository.save(anEntity);
	}

	@Override
	public User findByPk(Integer entityPk) {

		return userRepository.findById(entityPk).orElseThrow(() -> new DataNotFoundException("User details not found"));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);

	}

	@Override
	public User findByMobile(String mobile) {
		return userRepository.findByMobile(mobile);

	}

	@Override
	public List<User> getVerifiedActiveAgent(Integer userid) {

		return userRepository.getVerifiedActiveAgent(userid).orElse(Collections.emptyList());
	}

	@Override
	public List<User> getAllVerifiedActiveAgent() {

		return userRepository.getAllVerifiedActiveAgent().orElse(Collections.emptyList());
	}

}
