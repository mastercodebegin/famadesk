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
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_content")
public class EmailContent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "sender_email")
	private String senderEmail;
	@Column(name = "sent_on")
	private Date sentOn;
	
	@Column(name = "content",length=5000)
	private String content;
	
	private String subject;
	
	@Column(name = "message_unique_id")
	private String messageUniqueId;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "reply_by")
	@Basic(fetch = FetchType.LAZY)
	private User replyBy;
	
	
	@ManyToOne
	@JoinColumn(name = "ticket_id")
	@Basic(fetch = FetchType.LAZY)
	private TicketDetails ticketDetails;

}
