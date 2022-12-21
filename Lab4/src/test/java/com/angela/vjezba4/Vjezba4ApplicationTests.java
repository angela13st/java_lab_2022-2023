package com.angela.vjezba4;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.angela.model.Address;
import com.angela.model.Client;
import com.angela.model.ElMeter;
import com.angela.repository.AddressRepository;
import com.angela.repository.ClientRepository;
import com.angela.repository.ElMeterRepository;
import com.angela.service.ClientService;
import com.angela.service.ElMeterService;

@SpringBootTest
class Vjezba4ApplicationTests {

	@Autowired
	private ElMeterService smartDeviceService;
	@Autowired
	private ClientService clientService;

	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ElMeterRepository deviceRepository;

	private Address address;
	private Client client;
	private ElMeter smartDevice;

	@BeforeEach
	public void setUp() {
		address = new Address("streetName","streetNum", "city", "zipCode", "country");
		client = new Client("clientData", address);
		smartDevice = new ElMeter();
		client.setSmartDevice(smartDevice);
	}
	@AfterEach
	public void tearDown() {
		addressRepository.deleteAll();
		clientRepository.deleteAll();
		deviceRepository.deleteAll();
		address = null;
		client = null;
		smartDevice = null;
	}

	@Test
	void contextLoads() {
		assertThat(smartDeviceService).isNotNull();
		assertThat(clientService).isNotNull();
	}
	//client tests
	@Test
	void clientControllerGetNoDeviceHistory() {
		clientRepository.save(client);
		assertThat( clientService.getHistoryForClientsDevice(client.getClientID()) ).isEmpty();
	}
	@Test
	void clientControllerGetDeviceHistoryExists() {
		client.getSmartDevice().getHistory().add(1);
		clientRepository.save(client);
		assertThat( clientService.getHistoryForClientsDevice(client.getClientID()) ).contains(1);
	}
	@Test
	void clientControllerAddNewClientDevice() {
		clientRepository.save(client);
		clientService.registerNewSmartDeviceForClient(client.getClientID());
		assertThat( client.getSmartDevice() ).isNotNull();
	}

	//smart device tests

	@Test
	void smartDeviceControllerAddNewValue() {
		deviceRepository.save(smartDevice);
		smartDeviceService.addNewValueForDevice(smartDevice.getSmartDeviceID());
		assertThat(smartDevice.getHistory().get(0)).isNotNull();
	}
	@Test
	void smartDeviceControllergetAll() {
		deviceRepository.save(smartDevice);
		assertThat(smartDeviceService.getAllDevices()).isNotNull();
	}
}
