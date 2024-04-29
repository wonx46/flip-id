package com.flip.iwan.bean;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferPayload {

	private String to_username;
	
	private BigDecimal amount;
}
