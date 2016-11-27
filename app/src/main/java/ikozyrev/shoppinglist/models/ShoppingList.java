package ikozyrev.shoppinglist.models;

import java.io.Serializable;

/**
 * Created in Android Studia
 * User: ikozyrev
 * Date: 27.11.2016.
 */
public class ShoppingList implements Serializable {

    private int id;
    private String name;
    private String dsc;
    private int status;

    public ShoppingList(String name, String dsc) {
        this.name = name;
        this.dsc = dsc;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDsc() {
        return dsc;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
