package com.fama.famadesk.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fama.famadesk.enums.UserStatus;
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
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userid;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	private String mobile;

	private String gender;

	@JsonIgnore
	@Column(name = "joining_date")
	private Date joiningDate;

	@JsonIgnore
	@Column(name = "leaving_date")
	private Date leavingDate;

	@JsonIgnore
	private String password;

	private String email;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "is_password_change_required")
	private boolean isPasswordChangeRequired;

	@JsonIgnore
	@Column(name = "last_login_time")
	private Date lastLoginTime;

	@Column(name = "user_status")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "country_id")
	@Basic(fetch = FetchType.LAZY)
	private Country country;

	@JsonIgnore
	@Column(name = "created_on")
	private Date createdOn;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "created_by")
	@Basic(fetch = FetchType.LAZY)
	private User createdBy;


	@JsonIgnore
	@ManyToMany
	@Basic(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "userid", referencedColumnName = "user_id") }, inverseJoinColumns = {
					@JoinColumn(name = "roleid", referencedColumnName = "role_id") })
	private Set<Roles> roles = new HashSet<Roles>();

//	@JsonIgnore
	public String getRoleName() {
		Roles[] rolesArr = this.roles.toArray(new Roles[0]);
		return rolesArr[0].getRoleName();
	}

	@JsonIgnore
	public String getUserFullName() {
		return (this.getFirstName() + " " + this.getLastName());
	}

}
