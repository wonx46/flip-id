package com.flip.iwan.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;

@Entity
@NamedQuery(name="User.findAll", query="SELECT v FROM User v")
@Table(name="user")
@Data
public class User {

	@Id
	private String id;
	private String name;
	private String username;
	private String phone;
	private BigDecimal balance;
	private Date created_date;
	
	public User() {
		this.id = generateId();
	}
	
	private String generateId() {
		long now = Calendar.getInstance().getTimeInMillis();
		return String.valueOf(now);
	}
	
	public void setGeneratedId() {
		this.id = generateId();
	}

}
