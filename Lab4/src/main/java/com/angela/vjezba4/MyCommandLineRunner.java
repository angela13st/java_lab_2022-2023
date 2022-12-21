package com.angela.vjezba4;

import java.util.ArrayList;

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
    public void run(String... args)  {
        //clientRepository.deleteAll();
        // addressRepository.deleteAll();

        Address address1 = new Address("Ilica", "123", "Zagreb", "10000", "Hrvatska");
        Address address2 = new Address("Vinkovacka", "29", "Split", "21000", "Hrvatska");
        Address address3 = new Address("Put Plokita", "54", "Split", "21000", "Hrvatska");

        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);


        ArrayList<ElMeter> allDevices = smartDeviceRepository.getAllDevices();


        clientRepository.save(new Client("Stipe Matic", address1, allDevices.get(0)));
        clientRepository.save(new Client("Angela Plazibat", address2, allDevices.get(1)));
        clientRepository.save(new Client("Igor Jerkov", address3));

    }

}
