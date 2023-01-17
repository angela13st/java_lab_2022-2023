package com.angela.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angela.model.Usage;
import com.angela.model.ElMeter;
import com.angela.repository.ClientRepository;
import com.angela.repository.ElMeterRepository;

@Service
public class ElMeterService {

	@Autowired
	private ElMeterRepository deviceRepository;

	@Autowired
	private ClientRepository clientRepository;

	public Optional<ElMeter> findDeviceByID(int deviceID) {
		return deviceRepository.findDeviceByID(deviceID);

	}

	public Optional<Integer> addNewValueForDevice(int deviceID, int month, int year) {
		Optional<Integer> newValue = deviceRepository.addNewValueForDevice(deviceID, month, year);

		newValue.ifPresent(newId -> {
			clientRepository.refreshClientDataWithDeviceData(deviceID, month, year, newId);// update clients.json if
		});

		return newValue;
	}

	public ArrayList<ElMeter> getAllDevices() {
		return deviceRepository.getAllDevices();
	}

	public ElMeter registerNewDevice() {
		return deviceRepository.registerNewDevice();
	}

	public Optional<ElMeter> updateValueForDevice(int deviceID, int month, int year, int value) {
		Optional<ElMeter> device = deviceRepository.updateValueForDevice(deviceID, month, year, value);
		if (device.isPresent()) {
			clientRepository.refreshClientDataWithDeviceData(deviceID, month, year, value);// update clients.json if //
																							// // value was changed
		}

		return device;
	}

	public Optional<String> deleteValueForDevice(int deviceID, Usage usage) {
		Optional<String> deleted = deviceRepository.deleteValueForDevice(deviceID, usage.getMonth(),
				usage.getYear());
		if (deleted.isPresent()) {
			clientRepository.refreshClientDataWithDeviceDataDeleted(deviceID, usage.getMonth(), usage.getYear());

		}

		return deleted;
	}

	public List<Map<String, Object>> getAllValueForDevice(Integer deviceID) {
		return deviceRepository.getAllValueForDevice(deviceID);
	}

	public Map<String, Object> getValueForDeviceForMonthAndYear(Integer deviceID, Integer month, Integer year) {
		return deviceRepository.getValueForDeviceForMonthAndYear(deviceID, month, year);
	}

	public Map<String, Object> getValueForDeviceForWholeYear(Integer deviceID, Integer year) {
		return deviceRepository.getValueForDeviceForWholeYear(deviceID, year);
	}

	public Map<String, Object> getSumForYear(Integer deviceID, Integer year) {
		return deviceRepository.getSumForYear(deviceID, year);
	}

}
