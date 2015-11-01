package com.grabhouse.grabhouse.utilities;

/**
 * Created by Riyas V on 10/31/2015.
 */
public class House {
    private int id;
    private String name;
    private String type;
    private String location;
    private String price;

    public House(int id, String name, String type, String location, String price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
