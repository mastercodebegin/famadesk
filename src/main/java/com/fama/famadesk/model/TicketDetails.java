package com.fama.famadesk.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fama.famadesk.enums.Priority;
import com.fama.famadesk.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_details")
public class TicketDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ticket_id")
	private String ticketId;

	@Column(name = "user_name")
	private String userName;

	private String subject;

	@Column(name = "unique_id")
	private Long uniqueId;

	@Column(name = "cc_email")
	private String ccEmail;

	@Column(name = "created_on")
	private Date createdOn;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Column(name = "ticket_status")
	@Enumerated(EnumType.STRING)
	private TicketStatus ticketStatus;

	@Column(name = "description", length = 5000)
	private String description;

	@Column(name = "customer_email")
	private String customerEmail;

	@Column(name = "customer_phone")
	private String customerPhone;

	@Column(name = "is_verified_customer", columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean isVerifiedCustomer;

	@Column(name = "updated_on")
	private Date updatedOn;

//	@Column(name = "associated_service")
//	@Enumerated(EnumType.STRING)
//	private AssociatedService associatedService;

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
	@JoinColumn(name = "added_by")
	@Basic(fetch = FetchType.LAZY)
	private User addedBy;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "associated_account")
	@Basic(fetch = FetchType.LAZY)
	private Account account;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "updated_by")
	@Basic(fetch = FetchType.LAZY)
	private User updatedBy;

	@JsonIgnore
	public String getAssigneeFullName() {
		if (this.getAssignedTo() != null) {
			return (this.getAssignedTo().getFirstName() + " " + this.getAssignedTo().getLastName());
		} else {
			return null;
		}
	}

}
