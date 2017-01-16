package by.epam.osipov.internet.provider.entity.impl;

import by.epam.osipov.internet.provider.entity.Entity;

/**
 * Created by Lenovo on 16.01.2017.
 */
public class City extends Entity{
    private String name;

    public City(String name) {
        this.name = name;
    }

    public City(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
