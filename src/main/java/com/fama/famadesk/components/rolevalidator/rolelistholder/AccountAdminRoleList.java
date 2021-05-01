package com.fama.famadesk.components.rolevalidator.rolelistholder;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AccountAdminRoleList implements RoleConstant {

	public List<String> getAccountAdminRoleList() {
		return ACCOUNT_ADMIN_ROLE_LIST;
	}
}
