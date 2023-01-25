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

	public void addNewHistoryEvent(Usage usage)
	{
		this.smartDevice.addToHistory(usage);
	}

	public void setSmartDevice(ElMeter smartDevice) {
		if (this.smartDevice == null) {
			this.smartDevice = smartDevice;
		} else {
			// not adding smart device to client that already has it
			System.out.println("Klijent vec ima uredaj!");
			return;
		}
	}
	public boolean equals(Object obj) {
		Client other = (Client) obj;
		if(this.getAddress().getCountryName()==(other.getAddress().getCountryName()))
			if(this.getAddress().getZipCode()==(other.getAddress().getZipCode()))
				if(this.getAddress().getCity()==(other.getAddress().getCity()))
					if(this.getAddress().getStreetName()==(other.getAddress().getStreetName()))
						if(this.getAddress().getStreetNumber()==(other.getAddress().getStreetNumber()))
							return true;
		return false;

	}
	
}
