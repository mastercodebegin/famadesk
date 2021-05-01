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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group_details")
public class GroupDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "group_name")
	private String groupName;

	@Column(name = "is_removed", columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean isRemoved;

	@Column(name = "created_on")
	@Temporal(TemporalType.DATE)
	private Date createdOn;

	@JsonIgnore
	@Column(name = "updated_on")
	// @Temporal(TemporalType.DATE)
	private Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "project_id")
	@Basic(fetch = FetchType.LAZY)
	private ProjectDetails projectDetails;

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

}
