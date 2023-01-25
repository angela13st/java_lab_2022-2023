package com.angela.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angela.errorwrapper.DataAlreadyExistsException;
import com.angela.errorwrapper.NoDataFoundException;
import com.angela.model.Client;
import com.angela.model.Usage;
import com.angela.model.ElMeter;
import com.angela.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {
	@Autowired
	private ClientService clientService;

	@PostMapping("/{clientID}")
	public ResponseEntity<ElMeter> registerNewSmartDeviceForClient(
			@PathVariable(value = "clientID") @NotNull(message="Please enter clientID!") Integer clientID) {
		ElMeter device = clientService.registerNewSmartDeviceForClient(clientID);
		if (device != null) {
			return new ResponseEntity<>(device, HttpStatus.OK);
		} else
			throw new DataAlreadyExistsException("Client already has device!");
	}

	@GetMapping(path = "/{clientID}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usage>> getDeviceHistory(@PathVariable(value = "clientID") Integer clientID) {
		List<Usage> history = clientService.getHistoryForClientsDevice(clientID);
		if (history != null) {
			return new ResponseEntity<>(history, HttpStatus.OK);
		} else
			throw new NoDataFoundException("No client with that ID exists!");
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Client>> getAllClients(
			@RequestParam(required = false, name = "page", defaultValue = "1") Integer page,	//starts from 1
			@RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(required = false, name = "sortBy", defaultValue = "ID") String sortBy,
			@RequestParam(required = false, name = "sortDir", defaultValue = "asc") String sortDir) {
		
		ArrayList<Client> allClients = clientService.getAllClientsSortedAndPaginated(page, limit, sortBy, sortDir);
		if (allClients != null) {
			return new ResponseEntity<>(allClients, HttpStatus.OK);
		} else
			throw new NoDataFoundException("No clients exist!");
	}

}
