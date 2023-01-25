package com.angela.sorter;


import java.util.ArrayList;
import java.util.Collections;

import com.angela.model.ElMeter;

public class ElMeterSorter {
	public static ArrayList<ElMeter> getSortedSmartDevicesByIDAsc(ArrayList<ElMeter> devices){
		Collections.sort(devices, ElMeterComparator.IDComparatorAsc);
        return devices;
	}
	public static ArrayList<ElMeter> getSortedSmartDevicesByIDDesc(ArrayList<ElMeter> devices){
		Collections.sort(devices, ElMeterComparator.IDComparatorDesc);
        return devices;
	}
	public static ArrayList<ElMeter> getSortedSmartDevicesByHistorySizeAsc(ArrayList<ElMeter> devices){
		Collections.sort(devices, ElMeterComparator.historySizeComparatorAsc);
        return devices;
	}
	public static ArrayList<ElMeter> getSortedSmartDevicesByHistorySizeDesc(ArrayList<ElMeter> devices){
		Collections.sort(devices, ElMeterComparator.historySizeComparatorDesc);
        return devices;
	}
}
