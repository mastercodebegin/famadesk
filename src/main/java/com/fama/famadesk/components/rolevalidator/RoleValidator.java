package com.fama.famadesk.components.rolevalidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fama.famadesk.components.rolevalidator.rolelistholder.AccessValidationRoleList;
import com.fama.famadesk.components.rolevalidator.rolelistholder.AccountAdminRoleList;
import com.fama.famadesk.components.rolevalidator.rolelistholder.AdminRoleList;
import com.fama.famadesk.components.rolevalidator.rolelistholder.AgentRoleList;
import com.fama.famadesk.components.rolevalidator.rolelistholder.ApplicationDetailsAccessRoleList;

@Service
public class RoleValidator implements IRoleValidator {

	@Autowired
	private ApplicationDetailsAccessRoleList applicationDetailsAccessRoleList;
	@Autowired
	private AdminRoleList adminRoleList;
	@Autowired
	private AccessValidationRoleList accessValidationRoleList;
	@Autowired
	private AgentRoleList agentRoleList;

	@Autowired
	private AccountAdminRoleList accountAdminRoleList;

	@Override
	public boolean validateAdminRole(String userRole) {
		boolean isAllowed = false;
		if (adminRoleList.getAdminRoleList().contains(userRole)) {
			isAllowed = true;
		}
		return isAllowed;
	}

	@Override
	public boolean validateAgentRole(String userRole) {

		boolean isEmployee = false;
		if (agentRoleList.getAgentRoleList().contains(userRole)) {
			isEmployee = true;
		}
		return isEmployee;
	}

	@Override
	public boolean checkIfAccessValidationRole(String userRole) {
		if (accessValidationRoleList.getAccessValidationRoleList().contains(userRole)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean validateApplicationDetailsAccessRole(String userRole) {
		boolean isAllowed = false;
		if (applicationDetailsAccessRoleList.getApplicationDetailsAccessRoleList().contains(userRole)) {
			isAllowed = true;
		}
		return isAllowed;
	}

	@Override
	public boolean ValidateAccAdminRole(String userRole) {
		if (accountAdminRoleList.getAccountAdminRoleList().contains(userRole)) {
			return true;
		}
		return false;
	}

}
