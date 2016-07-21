package com.joeldholmes.enums;

public enum QuranVersionEnum {
	PICKTHALL("pickthall"),
	SHAKIR("shakir");
	
	private String name;
	 
	QuranVersionEnum(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}
