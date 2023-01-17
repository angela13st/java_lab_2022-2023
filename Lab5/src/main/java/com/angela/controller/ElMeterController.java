package com.angela.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.angela.model.Usage;
import com.angela.model.ElMeter;
import com.angela.service.ElMeterService;

@RestController
@Validated
@RequestMapping("/smartdevices")
public class ElMeterController {

	@Autowired
	private ElMeterService deviceService;

	@GetMapping
	public ResponseEntity<List<ElMeter>> getAllDevices() {
		// instead of returning NO_CONTENT its common to return an empty list
		return ResponseEntity.ok(deviceService.getAllDevices());
	}

	@PostMapping("/{deviceID}")
	public ResponseEntity<String> addNewValueForDevice(@PathVariable(value = "deviceID") Integer deviceID,
			@RequestBody Usage usage) {
		return deviceService.addNewValueForDevice(deviceID, usage.getMonth(), usage.getYear())
				.map(r -> ResponseEntity.ok("Added new value: " + r))
				.orElse(ResponseEntity.badRequest().body("Value already exists or there is no device with that ID"));
	}

	@PutMapping("/{deviceID}")
	public ResponseEntity<ElMeter> updateSmartDevice(@PathVariable(value = "deviceID") Integer deviceID,
			@RequestBody Usage usage) {
		return deviceService.updateValueForDevice(deviceID, usage.getMonth(), usage.getYear(), usage.getValue())
				.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{deviceID}")
	public ResponseEntity<String> deleteValueForDevice(@PathVariable(value = "deviceID") Integer deviceID,
			@RequestBody Usage usage) {
		// response can not be null at this point
		return deviceService.deleteValueForDevice(deviceID, usage).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// query with validation
	// method that will get called on constraint validation exception (for example
	// when month in query is 0)
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleValidationFailure(ConstraintViolationException ex) {
		StringBuilder messages = new StringBuilder();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			messages.append(violation.getMessage());
		}

		return ResponseEntity.badRequest().body(messages.toString());
	}

	@GetMapping("/{deviceID}")
	public ResponseEntity<List<Map<String, Object>>> getValueForDeviceForMonthAndYear(
			@PathVariable(value = "deviceID") Integer deviceID,
			@RequestParam(required = true, name = "year") Optional<Integer> year,
			@RequestParam(required = false, name = "month") Optional<Integer> month,
			@RequestParam(required = false, name = "all") Optional<String> all) {

		return year.map(ye -> {
			if (all.isPresent()) {
				return List.of(deviceService.getSumForYear(deviceID, ye));
			} else {
				return month.map(m -> List.of(deviceService.getValueForDeviceForMonthAndYear(deviceID, m, ye)))
						.orElse(List.of(deviceService.getValueForDeviceForWholeYear(deviceID, ye)));
			}
		}).map(ResponseEntity::ok).orElse(ResponseEntity.ok(deviceService.getAllValueForDevice(deviceID)));
	}
}
