package com.fama.famadesk.service;

import java.util.List;

import com.fama.famadesk.model.GroupDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.requestobject.AddGroupRequest;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface IGroupDetailsService extends IBaseService<GroupDetails> {

	void addGroup(AddGroupRequest addGroupRequest, User loggedInUser);

	List<GroupDetails> getGroupByProjectId(Integer projectid, User loggedInUser);

	void removeGroup(Integer groupid, User loggedInUser);

}
