package com.crucifix.software.coffeeshop.model.reference;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class Beverage implements Serializable {

    private static final long serialVersionUID = -1;

    private String id;

    @PropertyName("Name")
    private String name;

    @PropertyName("Price")
    private double price;

    @PropertyName("Type")
    private String type;

    public Beverage() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Beverage beverage = (Beverage) o;

        return getId() != null ? getId().equals(beverage.getId()) : beverage.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Beverage{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
