package com.crucifix.software.coffeeshop.model.reference;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

import io.realm.RealmModel;

public class BeverageTopping implements Serializable, RealmModel {

    private static final long serialVersionUID = -1;

    private String id;

    @PropertyName("Name")
    private String name;

    @PropertyName("Price")
    private double price;

    public BeverageTopping() {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BeverageTopping that = (BeverageTopping) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BeverageTopping{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
