package com.fama.famadesk.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fama.famadesk.components.rolevalidator.IRoleValidator;
import com.fama.famadesk.dao.IUserDao;
import com.fama.famadesk.dao.IUserGroupDao;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.GroupDetails;
import com.fama.famadesk.model.TicketDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.model.UserGroup;
import com.fama.famadesk.requestobject.UserGroupRequest;
import com.fama.famadesk.service.IGroupDetailsService;
import com.fama.famadesk.service.ITicketDetailsService;
import com.fama.famadesk.service.IUserGroupService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserGroupServiceImpl implements IUserGroupService {

	@Autowired
	private IUserGroupDao userGroupDao;

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IGroupDetailsService groupDetailsService;

	@Autowired
	private IRoleValidator roleValidator;

	@Autowired
	private ITicketDetailsService ticketDetailsService;

	@Override
	public UserGroup create(UserGroup anEntity) {

		return userGroupDao.create(anEntity);
	}

	@Override
	public UserGroup update(UserGroup anEntity) {
		return userGroupDao.update(anEntity);
	}

	@Override
	public List<UserGroup> findAll() {
		return userGroupDao.findAll();
	}

	@Override
	public UserGroup findByPk(Integer entityPk) {
		return userGroupDao.findByPk(entityPk);
	}

//	public void checkIfGroupAllottedToUser(Integer groupid, Integer userid) {
//		UserGroup userGroup = userGroupDao.getDetailsByGroupAndUser(groupid, userid);
//		if (userGroup == null) {
//
//		}
//	}

	@Override
	public void addMember(User loggedInUser, Set<Integer> userList, GroupDetails group) {
		for (Integer userid : userList) {
			UserGroup userGroupDetail = userGroupDao.getDetailsByGroupAndUser(group.getId(), userid);
			User user = userDao.findByPk(userid);
			// This means user is not allocated to given group so we can add him/her
			if (userGroupDetail == null) {
				UserGroup userGroup = new UserGroup();
				userGroup.setUser(user);
				userGroup.setGroup(group);
				userGroup.setAddedOn(new Date());
				create(userGroup);
			}

			else {
				throw new BusinessValidationException(user.getUserFullName() + " is already added to group ");
			}
		}

	}

	@Override
	public void addMemberToGroup(UserGroupRequest addMemberRequest, User loggedInUser) {

		if (!roleValidator.checkIfAccessValidationRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}

		if (addMemberRequest.getUserList() == null || addMemberRequest.getUserList().isEmpty()) {
			throw new BusinessValidationException("Please add user to group");
		}

		GroupDetails group = groupDetailsService.findByPk(addMemberRequest.getGroupId());
		addMember(loggedInUser, addMemberRequest.getUserList(), group);

	}

	@Override
	public List<UserGroup> getUserGroupDetailByGroupid(Integer groupid, User loggedInUser) {
		return userGroupDao.getUserGroupDetailByGroupid(groupid);
	}

	@Override
	public void removeMember(UserGroupRequest removeMemberRequest, User loggedInUser) {

		if (!roleValidator.checkIfAccessValidationRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}

		if (removeMemberRequest.getUserList() == null || removeMemberRequest.getUserList().isEmpty()) {
			throw new BusinessValidationException("User list is empty");
		}

		GroupDetails group = groupDetailsService.findByPk(removeMemberRequest.getGroupId());

		// Purposely we have used blank string
		String assigneeNames = "";
		for (Integer userid : removeMemberRequest.getUserList()) {

			List<TicketDetails> ticketDetailsList = ticketDetailsService.getPendingTicketByAssignee(userid);

			if (!ticketDetailsList.isEmpty()) {
				User user = userDao.findByPk(userid);
				assigneeNames = assigneeNames + ", " + user.getUserFullName();

			}

			else {
				// safety check
				UserGroup userGroup = userGroupDao.getDetailsByGroupAndUser(group.getId(), userid);
				if (userGroup != null) {
					userGroup.setUserRemoved(true);
					userGroup.setUpdatedOn(new Date());
					userGroup.setUpdatedBy(loggedInUser);
					update(userGroup);
				} else {
					log.info("User group detail not found by userid : {}, hence skipping", userid);
				}
			}

		}
		if (assigneeNames != null && !assigneeNames.trim().isEmpty()) {
			throw new BusinessValidationException(
					"Mentioned agents have some pending tickets to be completed ,i.e can not remove from group"
							+ assigneeNames);
		}

	}

}
