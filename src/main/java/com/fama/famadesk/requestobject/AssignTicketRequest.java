package com.fama.famadesk.requestobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignTicketRequest {

	private Integer ticketDetailId;
	private Integer assignedTo;

}
