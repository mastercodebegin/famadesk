package com.fama.famadesk.components.rolevalidator.rolelistholder;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ApplicationDetailsAccessRoleList implements RoleConstant {

	public List<String> getApplicationDetailsAccessRoleList() {
		return APPLICATION_DETAIL_ACCESS_ROLE_LIST;
	}

}