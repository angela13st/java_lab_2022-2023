package com.angela.vjezba5;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.angela.model.Address;
import com.angela.model.Client;
import com.angela.model.ElMeter;
import com.angela.repository.AddressRepository;
import com.angela.repository.ClientRepository;
import com.angela.repository.ElMeterRepository;

@Component
public class MyCommandLineRunner implements CommandLineRunner{

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private ElMeterRepository smartDeviceRepository;
	
	
	@Override
	public void run(String... args) throws Exception {


		Address address1 = new Address("Ilica", "123", "Zagreb", "10000", "Hrvatska");
		Address address2 = new Address("Vinkovacka", "29", "Split", "21000", "Hrvatska");
		Address address3 = new Address("Vukovarska", "125", "Split", "21000", "Hrvatska");

		addressRepository.save(address1);
		addressRepository.save(address2);
		addressRepository.save(address3);


		ArrayList<ElMeter> allDevices = smartDeviceRepository.getAllDevices();


		clientRepository.save(new Client("Stipe Matic", address1, allDevices.get(0)));
		clientRepository.save(new Client("Angela Plazibat", address2, allDevices.get(1)));
		clientRepository.save(new Client("Igor Jerkov", address3));
		List<Client> allClients = clientRepository.getAllClients();

		smartDeviceRepository.addNewValueForDevice(allClients.get(1).getSmartDevice().getSmartDeviceID(), 1, 2022);


		System.out.println(smartDeviceRepository.getValueForDeviceForWholeYear(allDevices.get(0).getSmartDeviceID(), 2022));
		System.out.println(smartDeviceRepository.getValueForDeviceForMonthAndYear(allDevices.get(0).getSmartDeviceID(), 1,2022));
		System.out.println(smartDeviceRepository.getSumForYear(allDevices.get(0).getSmartDeviceID(), 2022));
		System.out.println(smartDeviceRepository.getValueForDeviceForWholeYear(allDevices.get(1).getSmartDeviceID(), 2022));


	}

}
