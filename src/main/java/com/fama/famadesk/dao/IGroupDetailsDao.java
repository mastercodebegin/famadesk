package com.fama.famadesk.dao;

import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.GroupDetails;

public interface IGroupDetailsDao extends IBaseDao<GroupDetails> {

	List<GroupDetails> getGroupByProjectId(Integer projectid);

	GroupDetails getDetailsByGroupAndAccountAdmin(Integer groupid, Integer accountAdminId);

}
