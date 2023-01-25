package com.angela.service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angela.model.Client;
import com.angela.model.Usage;
import com.angela.model.ElMeter;
import com.angela.repository.ClientRepository;
import com.angela.sorter.ClientSorter;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	public List<Usage> getHistoryForClientsDevice(int clientID) {
		return clientRepository.getHistoryForClientsDevice(clientID);
	}

	public ElMeter registerNewSmartDeviceForClient(int clientID) {
		return clientRepository.registerNewDeviceForClient(clientID);
	}

	public ArrayList<Client> getAllClients() {
		return clientRepository.getAllClients();
	}

	// paginacija
	public ArrayList<Client> getAllClientsPaginated(Integer page, Integer limit) {
		ArrayList<Client> list = getAllClients();
		if (page != null) {
			ArrayList<Client> paginated = getPaginatedSubList(list, page, limit);
			return paginated;
		} else
			return list;
	}

	// sortiranje
	public ArrayList<Client> getAllClientsSortedAndPaginated(Integer page, Integer limit, String sortBy, String sortDir) {
		ArrayList<Client> sorted = getSortedList(getAllClientsPaginated(page, limit), sortBy, sortDir);
		return sorted;
	}

	private ArrayList<Client> getSortedList(ArrayList<Client> list, String sortBy, String sortDir) {

		if (sortBy.equalsIgnoreCase("ID")) {
			if (sortDir.equalsIgnoreCase("asc"))
				return ClientSorter.getSortedClientsByIDAsc(list);
			else
				return ClientSorter.getSortedClientsByIDDesc(list);
		} else {
			if (sortDir.equalsIgnoreCase("asc"))
				return ClientSorter.getSortedClientsByClientDataAsc(list);
			else
				return ClientSorter.getSortedClientsByClientDataDesc(list);
		}

	}

	private ArrayList<Client> getPaginatedSubList(ArrayList<Client> list, Integer page, Integer limit) {
		int totalLimit = list.size();
		int indexFrom = (page - 1) * limit;
		int indexTo = indexFrom + limit;
		if (indexFrom <= totalLimit) {
			if (indexTo > totalLimit) {
				indexTo = totalLimit;
			}
			return new ArrayList<Client>(list.subList(indexFrom, indexTo));
		}
		return new ArrayList<Client>();
	}

}
