package by.epam.osipov.internet.provider.entity.impl;

import by.epam.osipov.internet.provider.entity.Entity;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * Created by Lenovo on 11.01.2017.
 */
public class Contract extends Entity {

    private int idUser;
    private int idCoverage;
    private int apartmentNumber;
    private int idService;
    private int idAccess;
    private Timestamp serviceProvisionDate;

    public Contract(int idUser, int idCoverage, int apartmentNumber, int idService, int idAccess, Timestamp serviceProvisionDate) {
        this.idUser = idUser;
        this.idCoverage = idCoverage;
        this.apartmentNumber = apartmentNumber;
        this.idService = idService;
        this.idAccess = idAccess;
        this.serviceProvisionDate = serviceProvisionDate;
    }

    public Contract(int id, int idUser, int idCoverage, int apartmentNumber, int idService, int idAccess, Timestamp serviceProvisionDate) {
        super(id);
        this.idUser = idUser;
        this.idCoverage = idCoverage;
        this.apartmentNumber = apartmentNumber;
        this.idService = idService;
        this.idAccess = idAccess;
        this.serviceProvisionDate = serviceProvisionDate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCoverage() {
        return idCoverage;
    }

    public void setIdCoverage(int idCoverage) {
        this.idCoverage = idCoverage;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdAccess() {
        return idAccess;
    }

    public void setIdAccess(int idAccess) {
        this.idAccess = idAccess;
    }

    public Timestamp getServiceProvisionDate() {
        return serviceProvisionDate;
    }

    public void setServiceProvisionDate(Timestamp serviceProvisionDate) {
        this.serviceProvisionDate = serviceProvisionDate;
    }
}
