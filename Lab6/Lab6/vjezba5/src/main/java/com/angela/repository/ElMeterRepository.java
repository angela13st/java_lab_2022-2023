package com.angela.repository;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.angela.errorwrapper.DataAlreadyExistsException;
import com.angela.errorwrapper.NoDataFoundException;
import com.angela.model.MonthNames;
import com.angela.model.Usage;
import com.angela.model.ElMeter;

@Repository
public class ElMeterRepository {

	String filePath = "smartdevices.json";
	Gson gson;
	ArrayList<ElMeter> allDevices;
	Random random;

	public ElMeterRepository() {
		gson = new GsonBuilder().setPrettyPrinting().create();
		random = new Random();

		allDevices = new ArrayList<>();
		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ElMeter[] array = (gson.fromJson(new FileReader(filePath), ElMeter[].class));
			if (array != null) {
				for (ElMeter smartDevice : array) {
					allDevices.add(smartDevice);
				}
			}
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ElMeter> getAllDevices() {
		return allDevices;
	}

	public String getAllDevicesAsJson() {
		String convertedObject = new GsonBuilder().setPrettyPrinting().create().toJson(allDevices);
		return convertedObject;
		// return allDevices;
	}  

	public ElMeter registerNewDevice() {
		int maxID = 0;
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() >= maxID) {
				maxID = smartDevice.getSmartDeviceID() + 1;
			}
		}
		if (maxID != 0) {
			ElMeter newSmartDevice = new ElMeter(maxID);
			save(newSmartDevice);
			return newSmartDevice;
		}
		ElMeter newDevice = new ElMeter();
		save(newDevice);
		return newDevice;

	}

	public int addNewValueForDevice(int deviceID, int month, int year) {
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {

				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {
						// already exists
						System.out.println("Found month.year pair, not adding new value...");

						throw new DataAlreadyExistsException("Data for this month/year already exists!");
					}
				}
				// no reading for that month/year, put random value

				int randomInt = random.nextInt(500);
				smartDevice.getHistory().add(new Usage(month, year, randomInt)); // 0-500
				save();
				return randomInt;
			}
		}
		throw new NoDataFoundException("No data for this query - device requested doesn't exist");
	}

	public ElMeter findDeviceByID(int deviceID) {
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
			}
		}
		return null;
	}

	public void save() {
		try (FileWriter writer = new FileWriter(filePath)) {
			gson.toJson(allDevices, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void save(ElMeter smartDevice) {
		allDevices.add(smartDevice);
		try (FileWriter writer = new FileWriter(filePath)) {
			gson.toJson(allDevices, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void deleteAll() {
		allDevices.clear();
	}

	public ElMeter updateValueForDevice(int deviceID, int month, int year, int value) {
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {
						System.out.println("Found month.year pair, updating value...");
						usage.setValue(value);
						save();
						return smartDevice;
						//String convertedObject = new GsonBuilder().setPrettyPrinting().create().toJson(smartDevice);
						//return convertedObject;
					}
				}

				throw new NoDataFoundException(
						"No data for this query - month/year pair requested doesn't exist in history");
			}
		}

		throw new NoDataFoundException("No data for this query - device requested doesn't exist");
	}

	public String deleteValueForDevice(int deviceID, int month, int year) {
		Usage forRemoval = null;
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {
						System.out.println("Found month.year pair, deleting it...");
						forRemoval = usage;
					}
				}
				throw new NoDataFoundException(
						"No data for this query - month/year pair requested doesn't exist in history");
			}
			if (forRemoval != null) {
				smartDevice.getHistory().remove(forRemoval);
				save();
				return "Deleted value for month/year for device";
			}

		}  {
		throw new NoDataFoundException("No data for this query - device requested doesn't exist");
		}
		
	}

	public String getValueForDeviceForMonthAndYear(Integer deviceID, Integer month, Integer year) {
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {

						String json = String.format("{\"godina\": %d,\"mjesec\": %s, \"potrošnja\": %d}", year,
								MonthNames.valueOf(usage.getMonth()), usage.getValue());

						JsonObject convertedObject = new GsonBuilder().setPrettyPrinting().create().fromJson(json,
								JsonObject.class);

						return convertedObject.toString();

					}
				}
				throw new NoDataFoundException(
						"No data for this query - month/year pair requested doesn't exist in history");
			}
		}
		throw new NoDataFoundException("No data for this query - device requested doesn't exist");}
	

	public String getValueForDeviceForWholeYear(Integer deviceID, Integer year) {
		Map<String, Integer> valuesInMonths = new HashMap<String, Integer>();
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				valuesInMonths.put("godina", year);
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getYear() == year) {
						valuesInMonths.put(MonthNames.valueOf(usage.getMonth()), usage.getValue());
					}
				}
				String convertedObject = new GsonBuilder().setPrettyPrinting().create().toJson(valuesInMonths,
						new TypeToken<Map<String, Integer>>() {
						}.getType());
				return convertedObject;
			}
			throw new NoDataFoundException("No data for this query - year requested doesn't exist in history");
		}
		throw new NoDataFoundException("No data for this query - device requested doesn't exist");}
	

	public String getSumForYear(Integer deviceID, Integer year) {
		int sum = 0;
		boolean foundYear = false;
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getYear() == year) {
						foundYear = true;
						sum += usage.getValue();
					}
				}
				if (foundYear) {
					String json = String.format("{\"godina\": %d,\"ukupno potrošnja\": %d}", year, sum);
					JsonObject convertedObject = new GsonBuilder().setPrettyPrinting().create().fromJson(json,
							JsonObject.class);

					return convertedObject.toString();
					
				} else
					throw new NoDataFoundException("No data for this query - year requested doesn't exist in history");
			}
		}
		throw new NoDataFoundException("No data for this query - device requested doesn't exist");}
	

	public int addNewValueForDevice(Integer deviceID, int value, int month, int year) {
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {

				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {
						// already exists
						System.out.println("Found month.year pair, not adding new value...");

						throw new DataAlreadyExistsException("Data for this month/year already exists!");
					}
				}
				// no reading for that month/year, put value

				smartDevice.getHistory().add(new Usage(month, year, value)); // 0-500
				save();
				return value;
			}
		}
		throw new NoDataFoundException("No data for this query - device requested doesn't exist");}}
	

