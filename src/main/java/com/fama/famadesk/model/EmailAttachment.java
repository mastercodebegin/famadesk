package com.fama.famadesk.model;

import java.util.Date;

import javax.persistence.Basic;
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
@Table(name = "email_attachment")
public class EmailAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String attachment;

	private Date date;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "ticket_Details_id")
	@Basic(fetch = FetchType.LAZY)
	private TicketDetails ticketDetails;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "email_content_id")
	@Basic(fetch = FetchType.LAZY)
	private EmailContent emailContent;

}
