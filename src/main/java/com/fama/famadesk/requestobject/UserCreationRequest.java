package com.fama.famadesk.requestobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreationRequest {

	private String firstName;

	private String middleName;

	private String lastName;

	private String mobile;

	private String gender;

	private String email;

	private Integer countryId;
}
