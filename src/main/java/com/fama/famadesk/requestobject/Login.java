package com.fama.famadesk.requestobject;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
	@NotNull(message = "Email can not be null")
	private String email;

	@NotNull(message = "Password can not be null")
	private String password;
}
