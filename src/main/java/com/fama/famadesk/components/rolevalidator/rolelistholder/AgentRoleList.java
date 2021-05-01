package com.fama.famadesk.components.rolevalidator.rolelistholder;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AgentRoleList implements RoleConstant {
	public List<String> getAgentRoleList() {
		return AGENT_ROLE_LIST;
	}
}