package com.fama.famadesk.components.rolevalidator.rolelistholder;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AdminRoleList implements RoleConstant {
	public List<String> getAdminRoleList() {
		return ADMIN_ROLE_LIST;
	}
}