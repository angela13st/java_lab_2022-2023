package com.angela.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ElMeter {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(100);

    private int smartDeviceID;
    private List<Integer> history;

    public ElMeter() {
        this.smartDeviceID = ID_GENERATOR.getAndIncrement();
        this.history = new ArrayList<Integer>();
    }

    public ElMeter(int smartDeviceID) {
        this.smartDeviceID = smartDeviceID;
        this.history = new ArrayList<Integer>();
    }

    public ElMeter(List<Integer> history) {
        this.smartDeviceID = ID_GENERATOR.getAndIncrement();
        this.history = history;
    }
    public ElMeter(int smartDeviceID, List<Integer> history) {
        this.smartDeviceID = smartDeviceID;
        this.history = history;
    }
    public int getSmartDeviceID() {
        return smartDeviceID;
    }
    public void setSmartDeviceID(int smartDeviceID) {
        this.smartDeviceID = smartDeviceID;
    }
    public List<Integer> getHistory() {
        return history;
    }
    public void setHistory(List<Integer> history) {
        this.history = history;
    }


}
