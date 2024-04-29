package com.flip.iwan.enumz;

public enum EnumStsTrx implements IType {
	
	PENDING("PENDING"), SUCCESS("SUCCESS"),FAILED("FAILED");
	
	
	private String name;

	private EnumStsTrx(String name) {
		this.name = name;
	}
	
	public String getString() {
		// TODO Auto-generated method stub
		return name;
	}

	public String[] getItems() {
		// TODO Auto-generated method stub
		return new String[] {  PENDING.getString() , SUCCESS.getString(), FAILED.getString()};
	}
	
}
