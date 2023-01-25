package com.angela.sorter;



import java.util.ArrayList;
import java.util.Collections;

import com.angela.model.Client;

public class ClientSorter {
	
	public static ArrayList<Client> getSortedClientsByIDAsc(ArrayList<Client> clients){
		Collections.sort(clients, ClientComparator.IDComparatorAsc);
        return clients;
	}
	public static ArrayList<Client> getSortedClientsByIDDesc(ArrayList<Client> clients){
		Collections.sort(clients, ClientComparator.IDComparatorDesc);
        return clients;
	}
	
	
	public static ArrayList<Client> getSortedClientsByClientDataAsc(ArrayList<Client> clients){
		Collections.sort(clients, ClientComparator.clientDataComparatorAsc);
        return clients;
	}
	public static ArrayList<Client> getSortedClientsByClientDataDesc(ArrayList<Client> clients){
		Collections.sort(clients, ClientComparator.clientDataComparatorDesc);
        return clients;
	}
	

}
