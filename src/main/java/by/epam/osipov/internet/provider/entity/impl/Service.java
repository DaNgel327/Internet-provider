package by.epam.osipov.internet.provider.entity.impl;

import by.epam.osipov.internet.provider.entity.Entity;

/**
 * Created by Lenovo on 18.01.2017.
 */
public class Service extends Entity {

    private String name;
    private String description;
    private String validity;
    private double cost;

    public Service(String name, String description, String validity, double cost) {
        this.name = name;
        this.description = description;
        this.validity = validity;
        this.cost = cost;
    }

    public Service(int id, String name, String description, String validity, double cost) {
        super(id);
        this.name = name;
        this.description = description;
        this.validity = validity;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
