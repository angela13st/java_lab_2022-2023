package com.angela.sorter;


import java.util.Comparator;

import com.angela.model.Client;

public class ClientComparator {
	// for sorting by ID asc
	public static Comparator<Client> IDComparatorAsc = new Comparator<Client>() {
		@Override
		public int compare(Client c1, Client c2) {
			if (c1.getClientID() < c2.getClientID())
				return -1;
			else if (c1.getClientID() > c2.getClientID())
				return 1;

			else
				return 0; // will never happen
		}
	};
	
	// for sorting by ID desc
		public static Comparator<Client> IDComparatorDesc = new Comparator<Client>() {
			@Override
			public int compare(Client c1, Client c2) {
				if (c1.getClientID() > c2.getClientID())
					return -1;
				else if (c1.getClientID() < c2.getClientID())
					return 1;

				else
					return 0; // will never happen
			}
		};

	// for sorting by client data ( name ) asc
	public static Comparator<Client> clientDataComparatorAsc = new Comparator<Client>() {
		@Override
		public int compare(Client c1, Client c2) {
			return c1.getClientData().compareTo(c2.getClientData());
		}
	};
	
	// for sorting by client data ( name ) desc
		public static Comparator<Client> clientDataComparatorDesc = new Comparator<Client>() {
			@Override
			public int compare(Client c1, Client c2) {
				return c2.getClientData().compareTo(c1.getClientData());
			}
		};
}
