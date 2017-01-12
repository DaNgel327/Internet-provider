package by.epam.osipov.internet.provider.entity.impl;

import by.epam.osipov.internet.provider.entity.Entity;

/**
 * Created by Lenovo on 11.01.2017.
 */
public class User extends Entity {

    private String sName;
    private String name;
    private String pName;
    private String passport;
    private String phone;
    private double balance;

    public User(int id) {
        super(id);
    }

    public User(int id, String sName, String name, String pName, String passport, String phone, double balance) {
        super(id);
        this.sName = sName;
        this.name = name;
        this.pName = pName;
        this.passport = passport;
        this.phone = phone;
        this.balance = balance;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
