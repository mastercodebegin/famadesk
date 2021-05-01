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
@Table(name = "agent_account_admin_mapping")
public class AgentAccountAdminMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "agent")
	@Basic(fetch = FetchType.LAZY)
	private User agent;

	@ManyToOne
	@JoinColumn(name = "associated_account_admin")
	@Basic(fetch = FetchType.LAZY)
	private User associatedAccountAdmin;

	@JsonIgnore
	@Column(name = "added_on")
	private Date addedOn;

	@Column(name = "is_agent_removed", columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean isAgentRemoved;

}
