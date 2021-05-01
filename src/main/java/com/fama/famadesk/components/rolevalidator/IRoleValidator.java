package com.fama.famadesk.components.rolevalidator;

public interface IRoleValidator {
	public boolean validateAdminRole(String userRole);

	public boolean validateApplicationDetailsAccessRole(String userRole);

	public boolean validateAgentRole(String userRole);

	public boolean checkIfAccessValidationRole(String userRole);

	boolean ValidateAccAdminRole(String userRole);

}