package com.angela.model;

public class Usage {
	private int month;
	private int year;
	private int value;
	
	
	
	public Usage() {
		
	}
	
	public Usage(int month, int year, int value) {
		this.month = month;
		this.year = year;
		this.value = value;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
}
