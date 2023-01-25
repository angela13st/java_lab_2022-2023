package com.angela.vjezba6.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import com.angela.vjezba6.validation.*;


//@ReadingYearAnnotation
@ReadingYearValidator(first = "month", second = "year", errorMessage = "Date cannot be in the future")
public class Usage implements Comparable<Usage> {
	
	@NotNull( message = "Month cannot be empty")
	@Min(value = 1, message = "Month must be a number equal or bigger than 1")
	@Max(value = 12, message = "Month must be a number equal or smaller than 12")
	private Integer month;
	@NotNull( message = "Year cannot be empty")
	@Min(value = 2000, message = "Year cannot be smaller than 2000")
	private Integer year;
	
	@NotNull( message = "Reading value cannot be empty")
	@Min(value = 0, message = "Value must be positive number")
	private Integer value;
	
	@NotNull( message = "Device ID value cannot be empty")
	private Integer deviceID;
	

	public Usage() {
	}

	public Usage(Integer month, Integer year, Integer value) {
		super();
		this.month = month;
		this.year = year;
		this.value = value;
	}
	public Usage(Integer month, Integer year, Integer value, Integer deviceID) {
		super();
		this.deviceID = deviceID;
		this.month = month;
		this.year = year;
		this.value = value;
	}


    public Integer getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public int compareTo(Usage o) {
		return this.month - o.month;
	}


}
