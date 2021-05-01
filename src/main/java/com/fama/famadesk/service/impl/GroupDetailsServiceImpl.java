package com.fama.famadesk.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.components.rolevalidator.IRoleValidator;
import com.fama.famadesk.dao.IGroupDetailsDao;
import com.fama.famadesk.exception.BusinessValidationException;
import com.fama.famadesk.model.GroupDetails;
import com.fama.famadesk.model.ProjectDetails;
import com.fama.famadesk.model.TicketDetails;
import com.fama.famadesk.model.User;
import com.fama.famadesk.model.UserGroup;
import com.fama.famadesk.requestobject.AddGroupRequest;
import com.fama.famadesk.service.IGroupDetailsService;
import com.fama.famadesk.service.IProjectDetailsService;
import com.fama.famadesk.service.ITicketDetailsService;
import com.fama.famadesk.service.IUserGroupService;

@Service
public class GroupDetailsServiceImpl implements IGroupDetailsService {

	@Autowired
	private IGroupDetailsDao groupDetailsDao;

	@Autowired
	private IUserGroupService userGroupService;

	@Autowired
	private IProjectDetailsService projectDetailsService;

	@Autowired
	private IRoleValidator roleValidator;

	@Autowired
	private ITicketDetailsService ticketDetailsService;

	@Override
	public GroupDetails create(GroupDetails anEntity) {

		return groupDetailsDao.create(anEntity);
	}

	@Override
	public GroupDetails update(GroupDetails anEntity) {
		return groupDetailsDao.update(anEntity);
	}

	@Override
	public List<GroupDetails> findAll() {
		return groupDetailsDao.findAll();
	}

	@Override
	public GroupDetails findByPk(Integer entityPk) {
		return groupDetailsDao.findByPk(entityPk);
	}

	@Override
	public void addGroup(AddGroupRequest addGroupRequest, User loggedInUser) {
		if (!roleValidator.checkIfAccessValidationRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}
		if (addGroupRequest.getGroupName() == null || addGroupRequest.getGroupName().trim().isEmpty()) {
			throw new BusinessValidationException("Group name is required");
		}
		ProjectDetails projectDetails = projectDetailsService.findByPk(addGroupRequest.getProjectId());
		GroupDetails group = new GroupDetails();
		group.setGroupName(addGroupRequest.getGroupName());
		group.setCreatedOn(new Date());
		group.setProjectDetails(projectDetails);
		group.setAddedBy(loggedInUser);
		create(group);

		if (addGroupRequest.getUserList() != null && !addGroupRequest.getUserList().isEmpty()) {
			userGroupService.addMember(loggedInUser, addGroupRequest.getUserList(), group);
		}
	}

	@Override
	public List<GroupDetails> getGroupByProjectId(Integer projectid, User loggedInUser) {

		return groupDetailsDao.getGroupByProjectId(projectid);
	}

	@Override
	public void removeGroup(Integer groupid, User loggedInUser) {

		if (!roleValidator.ValidateAccAdminRole(loggedInUser.getRoleName())) {
			throw new BusinessValidationException("Not authorised to proceed further");
		}
		GroupDetails groupDetails = groupDetailsDao.getDetailsByGroupAndAccountAdmin(groupid, loggedInUser.getUserid());
		List<UserGroup> userGroupList = userGroupService.getUserGroupDetailByGroupid(groupid, loggedInUser);

		processUserGroupList(userGroupList, loggedInUser);

		groupDetails.setRemoved(true);
		groupDetails.setUpdatedOn(new Date());
		groupDetails.setUpdatedBy(loggedInUser);
		update(groupDetails);

	}

	private void processUserGroupList(List<UserGroup> userGroupList, User loggedInUser) {
		// Purposely we have used blank string
		String assigneeNames = "";

		for (UserGroup userGroup : userGroupList) {

			List<TicketDetails> ticketDetailsList = ticketDetailsService
					.getPendingTicketByAssignee(userGroup.getUser().getUserid());

			if (!ticketDetailsList.isEmpty()) {
				assigneeNames = assigneeNames + ", " + userGroup.getUserFullName();

			} else {
				userGroup.setUserRemoved(true);
				userGroup.setUpdatedOn(new Date());
				userGroup.setUpdatedBy(loggedInUser);
				userGroupService.update(userGroup);
			}
		}

		if (assigneeNames != null && !assigneeNames.trim().isEmpty()) {
			throw new BusinessValidationException(
					"Mentioned agents have some pending tickets to be completed, i.e can not remove group"
							+ assigneeNames);
		}
	}

}
