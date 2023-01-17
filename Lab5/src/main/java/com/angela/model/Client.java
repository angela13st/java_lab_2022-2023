package com.angela.model;

import java.util.concurrent.atomic.AtomicInteger;


public class Client {
private static AtomicInteger ID_GENERATOR = new AtomicInteger(100);
	
	private int clientID;
	private String clientData;
	private Address address;
	private ElMeter smartDevice;
	
	
	public Client(String clientData, Address address) {
		super();
		this.clientID = ID_GENERATOR.getAndIncrement();
		this.clientData = clientData;
		this.address = address;
	}
	public Client(String clientData, Address address, ElMeter smartDevice) {
		super();
		this.clientID = ID_GENERATOR.getAndIncrement();
		this.clientData = clientData;
		this.address = address;
		this.smartDevice = smartDevice;
	}
	public Client(int clientID, String clientData, Address address, ElMeter smartDevice) {
		super();
		this.clientID = clientID;
		this.clientData = clientData;
		this.address = address;
		this.smartDevice = smartDevice;
	}
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public String getClientData() {
		return clientData;
	}
	public void setClientData(String clientData) {
		this.clientData = clientData;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public ElMeter getSmartDevice() {
		return smartDevice;
	}
	public void setSmartDevice(ElMeter smartDevice) {
		if(this.smartDevice == null) {
			this.smartDevice = smartDevice;
		}
		else {
			//not adding smart device to client that already has it
			System.out.println("Not adding smart device to client that already has it!");
			return;
		}
	}
	@Override
	public boolean equals(Object obj) {
		Client other= (Client) obj;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (this.getAddress().getCity() == null) {
			if (other.getAddress().getCity() != null)
				return false;
		} else if (!this.getAddress().getCity().equals(other.getAddress().getCity()))
			return false;
		if (this.getAddress().getCountryName() == null) {
			if (other.getAddress().getCountryName() != null)
				return false;
		} else if (!this.getAddress().getCountryName().equals(other.getAddress().getCountryName()))
			return false;
		if (this.getAddress().getStreetName() == null) {
			if (other.getAddress().getStreetName() != null)
				return false;
		} else if (!this.getAddress().getStreetName().equals(other.getAddress().getStreetName()))
			return false;
		if (this.getAddress().getStreetNumber() == null) {
			if (other.getAddress().getStreetNumber() != null)
				return false;
		} else if (!this.getAddress().getStreetNumber().equals(other.getAddress().getStreetNumber()))
			return false;
		if (this.getAddress().getZipCode() == null) {
			if (other.getAddress().getZipCode() != null)
				return false;
		} else if (!this.getAddress().getZipCode().equals(other.getAddress().getZipCode()))
			return false;
		return true;
	}
	
	
}
