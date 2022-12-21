package com.angela.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angela.model.Client;
import com.angela.model.ElMeter;
import com.angela.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<ElMeter> registerNewSmartDeviceForClient(int clientID) {
        ElMeter device = clientService.registerNewSmartDeviceForClient(clientID);
        if(device != null) {
            return new ResponseEntity<>(device, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping
    public ResponseEntity<List<Integer>> getDeviceHistory(Integer clientID) {
        List<Integer> history = clientService.getHistoryForClientsDevice(clientID);
        if(history != null) {
            return new ResponseEntity<>(history, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }
}
