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
@NamedQuery(name="Transaction.findAll", query="SELECT v FROM Transaction v")
@Table(name="transaction")
@Data
public class Transaction {

	@Id
	private String trx_id;
	private String user1;
	private String user2;
	private String trx_type;
	private String category;
	private String status;
	private String notes;
	private BigDecimal nominal;
	private Date created_date;
	
	public Transaction() {
		this.trx_id = generateId();
	}
	
	private String generateId() {
		long now = Calendar.getInstance().getTimeInMillis();
		return String.valueOf(now);
	}
	
	public void setGeneratedId() {
		this.trx_id = generateId();
	}

}
