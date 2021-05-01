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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "account_name")
	private String accountName;

	@Column(name = "organization_name")
	private String organizationName;

	@JsonIgnore
	private String uuid;

	@JsonIgnore
	@Column(name = "created_on")
	@Temporal(TemporalType.DATE)
	private Date createdOn;

	@Column(name = "is_removed", columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean isRemoved;

	@JsonIgnore
	@Column(name = "updated_on")
	// @Temporal(TemporalType.DATE)
	private Date updatedOn;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "created_by")
	@Basic(fetch = FetchType.LAZY)
	private User createdBy;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "updated_by")
	@Basic(fetch = FetchType.LAZY)
	private User updatedBy;

}
