package com.angela.vjezba6;

import java.util.ArrayList;

import com.angela.model.Usage;
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
		
		clientRepository.deleteAll();
		addressRepository.deleteAll();
		smartDeviceRepository.deleteAll();


		Address address1 = new Address("Ilica", "123", "Zagreb", "10000", "Hrvatska");
		Address address2 = new Address("Vinkovacka", "29", "Split", "21000", "Hrvatska");
		Address address3 = new Address("Vukovarska", "125", "Split", "21000", "Hrvatska");

		addressRepository.save(address1);
		addressRepository.save(address2);
		addressRepository.save(address3);

		Usage first1=new	Usage(1,2022,325);
		Usage second1=new Usage(2,2022,148);

		ArrayList<Usage> usage1 = new ArrayList<>();
		usage1.add(first1);
		usage1.add(second1);
		ArrayList<Usage> usage2 = new ArrayList<>();
		Usage first2=new	Usage(1,2022,875);
		Usage second2=new Usage(2,2022,624);
		usage2.add(first2);
		usage2.add(second2);
		ElMeter device1 = new ElMeter(usage1);
		ElMeter device2 = new ElMeter(usage2);
		ElMeter device3 = new ElMeter();
		smartDeviceRepository.save(device1);
		smartDeviceRepository.save(device2);
		smartDeviceRepository.save(device3);


		ArrayList<ElMeter> allDevices = smartDeviceRepository.getAllDevices();


		clientRepository.save(new Client("Stipe Matic", address1, allDevices.get(0)));
		clientRepository.save(new Client("Angela Plazibat", address2, allDevices.get(1)));
		clientRepository.save(new Client("Igor Jerkov", address3));



	}

}
