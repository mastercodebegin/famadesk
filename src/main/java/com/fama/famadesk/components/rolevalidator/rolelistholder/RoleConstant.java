package com.fama.famadesk.components.rolevalidator.rolelistholder;

import java.util.Arrays;
import java.util.List;

public interface RoleConstant {
	List<String> AGENT_ROLE_LIST = Arrays.asList("agent");
	List<String> ADMIN_ROLE_LIST = Arrays.asList("applicationAdmin");
	List<String> ACCOUNT_ADMIN_ROLE_LIST = Arrays.asList("accountAdmin");
	List<String> ACCESS_VALIDATION_ROLE_LIST = Arrays.asList("applicationAdmin", "accountAdmin");

	List<String> APPLICATION_DETAIL_ACCESS_ROLE_LIST = Arrays.asList("applicationAdmin", "accountAdmin", "agent");

}
