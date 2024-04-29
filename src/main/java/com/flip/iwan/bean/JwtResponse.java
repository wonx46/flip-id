package com.flip.iwan.bean;


import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = 5602069244475750567L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
