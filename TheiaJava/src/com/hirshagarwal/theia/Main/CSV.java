package com.hirshagarwal.theia.Main;

public class CSV {

	private String name, number, nearPlaque;
	
	public CSV(String name, String number, String nearPlaque) {
		this.name = name;
		this.number = number;
		this.nearPlaque = nearPlaque;
	}
	
	public String toString() {
		String output = name + "," + number + "," + nearPlaque + "\n";
		return output;
	}
	
}
