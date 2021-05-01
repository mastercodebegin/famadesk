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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users_address")
public class UserAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name="address_line1")
	private String addressLine1;
	@Column(name="address_line2")
	private String addressLine2;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="postal_code")
	private int postalCode;
	@Column(name="added_on")
	private Date addedOn;
	@Column(name="updated_on")
	private Date updatedOn;
	//private String userAddressType;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@Basic(fetch = FetchType.LAZY)
	private User user;
	

}
