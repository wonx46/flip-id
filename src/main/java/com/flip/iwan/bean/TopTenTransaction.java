package com.flip.iwan.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TopTenTransaction {

	private String username;
	
	private BigDecimal amount;
}
