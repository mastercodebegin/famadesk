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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_comment")
public class TicketComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String comment;

	@Column(name = "comment_on")
	private Date commentOn;

	@ManyToOne
	@JoinColumn(name = "ticket_Details_id")
	@Basic(fetch = FetchType.LAZY)
	private TicketDetails ticketDetails;

	@ManyToOne
	@JoinColumn(name = "commented_by")
	@Basic(fetch = FetchType.LAZY)
	private User commentedBy;

}
