package com.angela.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ElMeter {
	private static AtomicInteger ID_GENERATOR = new AtomicInteger(100);

	private int smartDeviceID;
	private List<Usage> history;


	public ElMeter() {
		super();
		this.smartDeviceID = ID_GENERATOR.getAndIncrement();
		// this.history = new HashMap<Pair<Integer,Integer>, Integer>();
		this.history = new ArrayList<>();
	}

	public ElMeter(int smartDeviceID) {
		super();
		this.smartDeviceID = smartDeviceID;
		// this.history = new HashMap<Pair<Integer,Integer>, Integer>();
		this.history = new ArrayList<>();
	}

	public ElMeter(ArrayList<Usage> history) {
		super();
		this.smartDeviceID = ID_GENERATOR.getAndIncrement();
		this.history = history;
	}

	public ElMeter(int smartDeviceID, int value, ArrayList<Usage> history) {
		super();
		this.smartDeviceID = smartDeviceID;
		this.history = history;
	}

	public int getSmartDeviceID() {
		return smartDeviceID;
	}

	public void setSmartDeviceID(int smartDeviceID) {
		this.smartDeviceID = smartDeviceID;
	}

	public List<Usage> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<Usage> history) {
		this.history = history;
	}

}
