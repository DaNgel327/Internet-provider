package by.epam.osipov.internet.provider.entity.impl;

import by.epam.osipov.internet.provider.entity.Entity;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class Coverage extends Entity {

    private int idCity;
    private String street;
    private int houseNumber;

    public Coverage(int idCity, String street, int houseNumber) {
        this.idCity = idCity;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Coverage(int id, int idCity, String street, int houseNumber) {
        super(id);
        this.idCity = idCity;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
}
