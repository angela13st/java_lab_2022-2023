package com.angela.sorter;


import java.util.Comparator;

import com.angela.model.ElMeter;

public class ElMeterComparator {
	// for sorting by ID asc
	public static Comparator<ElMeter> IDComparatorAsc = new Comparator<ElMeter>() {
		@Override
		public int compare(ElMeter c1, ElMeter c2) {
			if (c1.getSmartDeviceID() < c2.getSmartDeviceID())
				return -1;
			else if (c1.getSmartDeviceID() > c2.getSmartDeviceID())
				return 1;

			else
				return 0; // will never happen
		}
	};

	// for sorting by ID desc
		public static Comparator<ElMeter> IDComparatorDesc = new Comparator<ElMeter>() {
			@Override
			public int compare(ElMeter c1, ElMeter c2) {
				if (c1.getSmartDeviceID() > c2.getSmartDeviceID())
					return -1;
				else if (c1.getSmartDeviceID() < c2.getSmartDeviceID())
					return 1;

				else
					return 0; // will never happen
			}
		};
		
		
	// for sorting by number of readings ascending
	public static Comparator<ElMeter> historySizeComparatorAsc = new Comparator<ElMeter>() {
		@Override
		public int compare(ElMeter c1, ElMeter c2) {
			if (c1.getHistory().size() < c2.getHistory().size())
				return -1;
			else if (c1.getHistory().size() > c2.getHistory().size())
				return 1;
			else
				return 0;
		}
	};
	
	// for sorting by number of readings descending
		public static Comparator<ElMeter> historySizeComparatorDesc = new Comparator<ElMeter>() {
			@Override
			public int compare(ElMeter c1, ElMeter c2) {
				if (c1.getHistory().size() > c2.getHistory().size())
					return -1;
				else if (c1.getHistory().size() < c2.getHistory().size())
					return 1;
				else
					return 0;
			}
		};
}
