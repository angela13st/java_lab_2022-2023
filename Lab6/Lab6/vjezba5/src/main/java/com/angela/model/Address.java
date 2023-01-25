package com.angela.model;

import java.util.concurrent.atomic.AtomicInteger;


public class Address {
	private static AtomicInteger ID_GENERATOR = new AtomicInteger(100);
	
	private int addressID;
	private String streetName;
	private String streetNumber;
	private String city;
	private String zipCode;
	private String countryName;
	
	
	public Address(String streetName, String streetNumber, String city, String zipCode, String countryName) {
		super();
		this.addressID = ID_GENERATOR.getAndIncrement();
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.city = city;
		this.zipCode = zipCode;
		this.countryName = countryName;
	}
	public Address(int addressID, String streetName, String streetNumber, String city, String zipCode,
			String countryName) {
		super();
		this.addressID = addressID;
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.city = city;
		this.zipCode = zipCode;
		this.countryName = countryName;
	}
	
	
	public int getAddressID() {
		return addressID;
	}
	public void setAddressID(int addressID) {
		this.addressID = addressID;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
		result = prime * result + ((streetNumber == null) ? 0 : streetNumber.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		Address other = (Address) obj;
		if(this.getCountryName()==(other.getCountryName()))
			if(this.getZipCode()==(other.getZipCode()))
				if(this.getCity()==(other.getCity()))
					if(this.getStreetName()==(other.getStreetName()))
						if(this.getStreetNumber()==(other.getStreetNumber()))
							return true;
		return false;
	}
	
	
}
