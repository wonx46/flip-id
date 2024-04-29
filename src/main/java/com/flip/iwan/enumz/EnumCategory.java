package com.flip.iwan.enumz;

public enum EnumCategory implements IType {
	
	TOPUP("TOPUP"), TRANSFER("TRANSFER");
	
	
	private String name;

	private EnumCategory(String name) {
		this.name = name;
	}
	
	public String getString() {
		// TODO Auto-generated method stub
		return name;
	}

	public String[] getItems() {
		// TODO Auto-generated method stub
		return new String[] {  TOPUP.getString() , TRANSFER.getString()};
	}
	
}
