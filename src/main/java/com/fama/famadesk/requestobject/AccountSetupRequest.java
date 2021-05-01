package com.fama.famadesk.requestobject;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSetupRequest {

	private String accountName;

	private String organizationName;

	private Set<Integer> projectList;

}
