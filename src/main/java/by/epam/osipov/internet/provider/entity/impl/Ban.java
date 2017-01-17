package by.epam.osipov.internet.provider.entity.impl;

import by.epam.osipov.internet.provider.entity.Entity;

/**
 * Created by Lenovo on 17.01.2017.
 */
public class Ban extends Entity {

    private int idUser;
    private String description;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Ban(int idUser, String description) {
        this.idUser = idUser;
        this.description = description;
    }

    public Ban(int id, int idUser, String description) {
        super(id);
        this.idUser = idUser;
        this.description = description;
    }
}
