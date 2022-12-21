package com.angela.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Address {
    private static AtomicInteger ID_GENERATOR = new AtomicInteger(100);

    private int addressID;
    private String streetName;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String countryName;


    public Address(String streetName, String streetNumber, String city, String zipCode, String countryName) {
        super();
        this.addressID = ID_GENERATOR.getAndIncrement();
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.countryName = countryName;
    }
    public Address(int addressID, String streetName, String streetNumber, String city, String zipCode,
                   String countryName) {
        super();
        this.addressID = addressID;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.zipCode = zipCode;
        this.countryName = countryName;
    }


    public int getAddressID() {
        return addressID;
    }
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public String getStreetNumber() {
        return streetNumber;
    }
    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (countryName == null) {
            if (other.countryName != null)
                return false;
        } else if (!countryName.equals(other.countryName))
            return false;
        if (streetName == null) {
            if (other.streetName != null)
                return false;
        } else if (!streetName.equals(other.streetName))
            return false;
        if (streetNumber == null) {
            if (other.streetNumber != null)
                return false;
        } else if (!streetNumber.equals(other.streetNumber))
            return false;
        if (zipCode == null) {
            if (other.zipCode != null)
                return false;
        } else if (!zipCode.equals(other.zipCode))
            return false;
        return true;
    }


}
