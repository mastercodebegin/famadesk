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
@Table(name = "user_group")
public class UserGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "created_on")
	// @Temporal(TemporalType.DATE)
	private Date addedOn;

	@Column(name = "is_user_removed", columnDefinition = "tinyint(1) default 0", nullable = false)
	private boolean isUserRemoved;

	@Column(name = "updated_on")
	// @Temporal(TemporalType.DATE)
	private Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "group_id")
	@Basic(fetch = FetchType.LAZY)
	private GroupDetails group;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@Basic(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne
	@JoinColumn(name = "updated_by")
	@Basic(fetch = FetchType.LAZY)
	private User updatedBy;

	@JsonIgnore
	public String getUserFullName() {
		if (this.getUser() != null) {
			return (this.getUser().getFirstName() + " " + this.getUser().getLastName());
		} else {
			return null;
		}
	}

}
