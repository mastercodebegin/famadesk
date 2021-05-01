package com.fama.famadesk.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.dao.IRoleDao;
import com.fama.famadesk.model.Roles;
import com.fama.famadesk.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Override
	public Roles create(Roles anEntity) {
		return roleDao.create(anEntity);
	}

	@Override
	public Roles update(Roles anEntity) {
		return roleDao.update(anEntity);
	}

	@Override
	public List<Roles> findAll() {
		return roleDao.findAll();
	}

	@Override
	public Roles findByPk(Integer entityPk) {
		return roleDao.findByPk(entityPk);
	}

	@Override
	public List<Roles> getAvailableRoles() {
		return roleDao.getAvailableRoles();
	}

}
