package by.epam.osipov.internet.provider.entity.impl;


import by.epam.osipov.internet.provider.entity.Entity;

import java.sql.Timestamp;

/**
 * Created by Lenovo on 01.02.2017.
 */
public class Operation extends Entity {

    private int type;
    private Timestamp date;
    private double sum;
    private int userId;

    public Operation(int type, Timestamp date, double sum, int userId) {
        this.type = type;
        this.date = date;
        this.sum = sum;
        this.userId = userId;
    }

    public Operation(int id, int type, Timestamp date, double sum, int userId) {
        super(id);
        this.type = type;
        this.date = date;
        this.sum = sum;
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
