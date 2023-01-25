package com.angela.controller;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.angela.errorwrapper.DataAlreadyExistsException;
import com.angela.errorwrapper.NoDataFoundException;
import com.angela.model.Usage;
import com.angela.model.ElMeter;
import com.angela.service.ElMeterService;

@RestController
@Validated
@RequestMapping("/smartdevices")
public class ElMeterController {
	@Autowired
	private ElMeterService deviceService;

	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ElMeter>> getAllDevices(
			@RequestParam(required = false, name = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(required = false, name = "sortBy", defaultValue = "ID") String sortBy,
			@RequestParam(required = false, name = "sortDir", defaultValue = "asc") String sortDir) {

		ArrayList<ElMeter> devices = deviceService.getAllDevicesSortedAndPaginated(page,limit, sortBy, sortDir);
		
		if (devices != null) {
			return new ResponseEntity<>(devices, HttpStatus.OK);
		} else 
			throw new NoDataFoundException("No devices exist");
	}

	@PostMapping("/{deviceID}")
	public ResponseEntity<String> addNewValueForDevice(@PathVariable(value = "deviceID")  @NotNull(message="Please enter deviceID!")  Integer deviceID,
			@RequestBody Usage usage  ) {
		Integer value = deviceService.addNewValueForDevice(deviceID,usage.getValue(), usage.getMonth(), usage.getYear());
		if (value != -1) {
			return new ResponseEntity<>("Added new value: " + value, HttpStatus.OK);
		} else 
			throw new DataAlreadyExistsException("Value already exists or there is no device with that ID");		
	}

	@PutMapping(path = "/{deviceID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElMeter> updateSmartDevice(@PathVariable(value = "deviceID")  @NotNull(message="Please enter deviceID!")  Integer deviceID,
			@RequestBody Usage usage) {
		ElMeter updatedDevice = deviceService.updateValueForDevice(deviceID, usage.getMonth(), usage.getYear(),
				usage.getValue());
		if (updatedDevice != null) {
			return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
		} else
			throw new NoDataFoundException("Device for update or month/year not found");
	}

	@DeleteMapping(path = "/{deviceID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteValueForDevice(@PathVariable(value = "deviceID") @NotNull(message="Please enter deviceID!")  Integer deviceID,
			@RequestBody Usage usage) {
		String response = deviceService.deleteValueForDevice(deviceID, usage.getMonth(), usage.getYear());
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else
			throw new NoDataFoundException("Device or month/year not found");
	}

	// query with validation
	@GetMapping(path = "/{deviceID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getValueForDeviceForMonthAndYear(@PathVariable(value = "deviceID") @NotNull(message="Please enter deviceID!")  Integer deviceID,
			@RequestParam(required = true, name = "year") @Min(value = 2000, message = "Year must be greater than 2000") Integer year,

			@RequestParam(required = false, name = "month") @Min(value = 1, message = "Month must be equal or greater than 1") @Max(value = 12, message = "Month must be equal or less than 12") Integer month,
			
			@RequestParam(required = false, name = "all") Optional<String> all) {

		//if all is here, return all
		if(all.isPresent() == true) {
			String response = deviceService.getSumForYear(deviceID, year);
			if (response != null) {
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else
				throw new NoDataFoundException("No data for this query - device doesn't exist");
				
		}
		
		
		// no month - return whole year
		if (month == null) {

			String response = deviceService.getValueForDeviceForWholeYear(deviceID, year);
			if (response != null) {
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else
				throw new NoDataFoundException("No data for this query - device or year requested doesn't exist");
				
				//return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

				
			// return for asking month
		} else {

			String response = deviceService.getValueForDeviceForMonthAndYear(deviceID, month, year);
			if (response != null) {
				return new ResponseEntity<>(String.valueOf(response), HttpStatus.OK);
			} else
				throw new NoDataFoundException("No data for this query - device or month/year requested doesn't exist");
				
				//return new ResponseEntity<>("No data for this query", HttpStatus.BAD_REQUEST);
		}
	}
}
