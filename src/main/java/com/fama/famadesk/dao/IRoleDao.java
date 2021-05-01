package com.fama.famadesk.dao;

import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.Roles;

public interface IRoleDao extends IBaseDao<Roles> {
	List<Roles> getAvailableRoles();

}
