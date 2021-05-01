package com.fama.famadesk.components.rolevalidator.rolelistholder;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AccessValidationRoleList implements RoleConstant {

	public List<String> getAccessValidationRoleList() {
		return ACCESS_VALIDATION_ROLE_LIST;
	}

}