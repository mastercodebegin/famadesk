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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "project_details")
public class ProjectDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "officail_email")
	private String officialEmail;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String uuid;

	private String comment;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "added_by")
	@Basic(fetch = FetchType.LAZY)
	private User addedBy;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "updated_by")
	@Basic(fetch = FetchType.LAZY)
	private User updatedBy;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_associated")
	@Basic(fetch = FetchType.LAZY)
	private Account accountAssociated;

}
