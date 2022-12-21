package com.angela.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angela.model.ElMeter;
import com.angela.repository.ClientRepository;
import com.angela.repository.ElMeterRepository;

@Service
public class ElMeterService {
    @Autowired
    ElMeterRepository deviceRepository;
    @Autowired
    ClientRepository clientRepository;

    public ElMeter findDeviceByID(int deviceID) {
        return deviceRepository.findDeviceByID(deviceID);

    }
    public int addNewValueForDevice(int deviceID) {
        int newValue = deviceRepository.addNewValueForDevice(deviceID);
        clientRepository.refreshClientDataWithDeviceData(deviceID, newValue);
        return newValue;
    }

    public ArrayList<ElMeter> getAllDevices() {
        return deviceRepository.getAllDevices();
    }
    public ElMeter registerNewDevice() {
        return deviceRepository.registerNewDevice();
    }

}
