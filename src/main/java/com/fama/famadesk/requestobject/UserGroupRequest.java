package com.fama.famadesk.requestobject;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupRequest {

	private Set<Integer> userList;
	private Integer groupId;
}
