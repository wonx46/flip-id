package com.flip.iwan.enumz;

public enum EnumTypeTrx implements IType {
	
	DEBIT("DEBIT"), CREDIT("CREDIT");
	
	
	private String name;

	private EnumTypeTrx(String name) {
		this.name = name;
	}
	
	public String getString() {
		// TODO Auto-generated method stub
		return name;
	}

	public String[] getItems() {
		// TODO Auto-generated method stub
		return new String[] {  DEBIT.getString() , CREDIT.getString()};
	}
	
}
