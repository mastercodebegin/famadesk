package com.fama.famadesk.requestobject;

import com.fama.famadesk.enums.Priority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManualTicketCreateRequest {

	private Priority priority;
	private String description;
	private String customerPhone;
	private Integer assignedTo;

}
