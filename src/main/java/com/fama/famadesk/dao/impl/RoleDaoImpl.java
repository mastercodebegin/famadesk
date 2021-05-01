package com.fama.famadesk.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.dao.IRoleDao;
import com.fama.famadesk.exception.DataNotFoundException;
import com.fama.famadesk.model.Roles;
import com.fama.famadesk.repository.RoleRepository;

@Component
public class RoleDaoImpl implements IRoleDao {

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Roles create(Roles anEntity) {
		return roleRepository.save(anEntity);
	}

	@Override
	public Roles update(Roles anEntity) {

		return roleRepository.save(anEntity);
	}

	@Override
	public Roles findByPk(Integer entityPk) {
		return roleRepository.findById(entityPk).orElseThrow(() -> new DataNotFoundException("Role not found"));
	}

	@Override
	public List<Roles> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public List<Roles> getAvailableRoles() {
		return roleRepository.getAvailableRoles().orElseThrow(() -> new DataNotFoundException("No role details found"));
	}

}
