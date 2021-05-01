package com.fama.famadesk.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_assign_history")
public class TicketAssignHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "assigned_on")
	private Date assignedOn;

	@Column(name = "forwarded_on")
	private Date forwardedOn;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ticket_details_id")
	@Basic(fetch = FetchType.LAZY)
	private TicketDetails ticketDetails;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "assigned_to")
	@Basic(fetch = FetchType.LAZY)
	private User assignedTo;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "assigned_by")
	@Basic(fetch = FetchType.LAZY)
	private User assignedBy;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "updated_by")
	@Basic(fetch = FetchType.LAZY)
	private User updatedBy;

	@Column(name = "updated_on")
	private Date updatedOn;

}
