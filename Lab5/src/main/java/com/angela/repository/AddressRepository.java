package com.angela.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import com.angela.model.Address;

@Repository
public class AddressRepository {

	String filePath = "addresses.json";
	Gson gson;
	ArrayList<Address> allAddresses;

	public AddressRepository() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		allAddresses = new ArrayList<>();

		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// loadAll();
	}

	public void loadAll() {

		if (allAddresses.isEmpty()) {
			try {
				Address[] array = (gson.fromJson(new FileReader(filePath), Address[].class));
				if (array != null) {
					for (Address address : array) {
						allAddresses.add(address);
					}
				}
			} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteAll() {
		allAddresses.clear();
	}

	public void save(Address address) {
		for (Address existingAddress : allAddresses) {
			if (existingAddress.equals(address)) {
				// not saving duplicate address
				System.out.println("Not saving duplicate address!");
				return;
			}
		}

		allAddresses.add(address);

		try (FileWriter writer = new FileWriter(filePath)) {
			gson.toJson(allAddresses, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
