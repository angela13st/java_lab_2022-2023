package com.angela.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import com.angela.model.Client;
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

    public int addNewValueForDevice(int deviceID) {
        for (ElMeter smartDevice : allDevices) {
            if (smartDevice.getSmartDeviceID() == deviceID) {
                int randomInt = random.nextInt(500);
                smartDevice.getHistory().add(randomInt); // 0-500
                save();
                return randomInt;
            }
        }
        return 0;
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
}
