package com.fama.famadesk.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fama.famadesk.model.Roles;
import com.fama.famadesk.service.baseservice.IBaseService;

@Component
public interface IRoleService extends IBaseService<Roles> {

	List<Roles> getAvailableRoles();

}
