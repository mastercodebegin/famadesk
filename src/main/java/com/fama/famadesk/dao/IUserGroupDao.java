package com.fama.famadesk.dao;

import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.UserGroup;

public interface IUserGroupDao extends IBaseDao<UserGroup> {

	public UserGroup getDetailsByGroupAndUser(Integer groupid, Integer userid);

	public List<UserGroup> getUserGroupDetailByGroupid(Integer groupid);

}
