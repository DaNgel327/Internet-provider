package by.epam.osipov.internet.provider.entity;

/**
 * Created by Lenovo on 11.01.2017.
 */
public abstract class Entity {

    private int id;

    public Entity(){

    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
