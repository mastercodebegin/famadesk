package com.fama.famadesk.requestobject;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGroupRequest {

	private String groupName;
	private Integer projectId;

	// This is optional
	private Set<Integer> userList;

}
