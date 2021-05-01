package com.fama.famadesk.service;

import java.util.List;
import java.util.Set;

import com.fama.famadesk.model.GroupDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.model.UserGroup;
import com.fama.famadesk.requestobject.UserGroupRequest;
import com.fama.famadesk.service.baseservice.IBaseService;

public interface IUserGroupService extends IBaseService<UserGroup> {

	void addMember(User loggedInUser, Set<Integer> userList, GroupDetails group);

	void addMemberToGroup(UserGroupRequest addMemberRequest, User loggedInUser);

	List<UserGroup> getUserGroupDetailByGroupid(Integer groupid, User loggedInUser);

	void removeMember(UserGroupRequest removeMemberRequest, User loggedInUser);

}
