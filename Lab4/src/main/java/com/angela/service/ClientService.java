package com.angela.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angela.model.Client;
import com.angela.model.ElMeter;
import com.angela.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public List<Integer> getHistoryForClientsDevice(int clientID){
        return clientRepository.getHistoryForClientsDevice(clientID);
    }

    public ElMeter registerNewSmartDeviceForClient(int clientID) {
        return clientRepository.registerNewDeviceForClient(clientID);
    }

    public List<Client> getAllClients() {
        return clientRepository.getAllClients();
    }
}
