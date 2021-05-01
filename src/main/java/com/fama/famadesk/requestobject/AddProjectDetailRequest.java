package com.fama.famadesk.requestobject;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProjectDetailRequest {

	private String projectName;

	private String officialEmail;

	@Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%,\\];:+=?_|{}~*&()\\[\\-]).{8,20})"
			+ "", message = "Please input valid password.")
	private String password;

}
