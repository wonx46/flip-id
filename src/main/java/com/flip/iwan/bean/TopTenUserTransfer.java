package com.flip.iwan.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TopTenUserTransfer {

	private String username;
	
	private BigDecimal transacted_value;
}
