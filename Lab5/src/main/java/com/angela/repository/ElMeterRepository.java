package com.angela.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import com.angela.model.MonthNames;
import com.angela.model.Usage;
import com.angela.model.ElMeter;

@Repository
public class ElMeterRepository {

	private static final String filePath = "smartdevices.json";
	private static Gson gson;
	private static ArrayList<ElMeter> allDevices;
	private static Random random;

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

	public Optional<Integer> addNewValueForDevice(int deviceID, int month, int year) {
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {

				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {
						// already exists
						System.out.println("Found month.year pair, not adding new value...");
						return Optional.empty();
					}
				}
				// no reading for that month/year, put random value

				Integer randomInt = random.nextInt(500);
				smartDevice.getHistory().add(new Usage(month, year, randomInt)); // 0-500
				save();
				return Optional.of(randomInt);
			}
		}
		return Optional.empty();
	}

	public Optional<ElMeter> findDeviceByID(int deviceID) {
		return allDevices.stream().filter(device -> device.getSmartDeviceID() == deviceID).findFirst();
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

	public Optional<ElMeter> updateValueForDevice(int deviceID, int month, int year, int value) {
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {
						System.out.println("Found month.year pair, updating value...");
						usage.setValue(value);
						save();
						return Optional.of(smartDevice);
					}
				}
			}
		}
		return Optional.empty();

	}

	public Optional<String> deleteValueForDevice(int deviceID, int month, int year) {
		Usage forRemoval = null;
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {
						System.out.println("Found month.year pair, deleting it...");
						forRemoval = usage;
					}
				}
			}
			if (forRemoval != null) {
				smartDevice.getHistory().remove(forRemoval);
				save();
				return Optional.of("Deleted value for month/year for device");
			}

		}
		return Optional.empty();
	}

	public Map<String, Object> getValueForDeviceForMonthAndYear(Integer deviceID, Integer month, Integer year) {

		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getMonth() == month && usage.getYear() == year) {

						return Map.of("godina", year, "mjesec", MonthNames.valueOf(usage.getMonth()), "potrošnja",
								usage.getValue());

					}
				}
			}
		}
		return Map.of("godina", year);
	}

	public Map<String, Object> getValueForDeviceForWholeYear(Integer deviceID, Integer year) {
		Map<String, Object> valuesInMonths = new HashMap<>();
		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				valuesInMonths.put("godina", year);
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getYear() == year) {
						valuesInMonths.put(MonthNames.valueOf(usage.getMonth()), usage.getValue());
					}
				}
				return valuesInMonths;
			}
		}
		return null;
	}

	public List<Map<String, Object>> getAllValueForDevice(Integer deviceID) {
		List<Map<String, Object>> result = new ArrayList<>();

		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {

				List<Integer> years = smartDevice.getHistory().stream().map(Usage::getYear).distinct()
						.collect(Collectors.toList());

				years.forEach(y -> {
					Map<String, Object> valuesInMonths = new HashMap<>();
					valuesInMonths.put("godina", y);

					smartDevice.getHistory().stream().filter(read -> read.getYear() == y)
							.forEach(m -> valuesInMonths.put(MonthNames.valueOf(m.getMonth()), m.getValue()));

					result.add(valuesInMonths);
				});
			}
		}
		return result;
	}

	public Map<String, Object> getSumForYear(Integer deviceID, Integer year) {
		int sum = 0;

		for (ElMeter smartDevice : allDevices) {
			if (smartDevice.getSmartDeviceID() == deviceID) {
				for (Usage usage : smartDevice.getHistory()) {
					if (usage.getYear() == year) {
						sum += usage.getValue();
					}
				}
				return Map.of("godina", year, "ukupno potrošnja", sum);
			}
		}
		return null;
	}
}
