package com.fama.famadesk.dao;

import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.User;

public interface IUserDao extends IBaseDao<User> {
	public User findByEmail(String email);

	User findByMobile(String mobile);

	public List<User> getVerifiedActiveAgent(Integer userid);

	List<User> getAllVerifiedActiveAgent();
}
