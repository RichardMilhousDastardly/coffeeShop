package com.crucifix.software.coffeeshop.value.objects;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class BeverageToppingRO implements RealmModel {

    private String toppingName;

    public BeverageToppingRO() {
    }

    public BeverageToppingRO(final String toppingName) {
        this.toppingName = toppingName;
    }

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(final String toppingName) {
        this.toppingName = toppingName;
    }

    @Override
    public String toString() {
        return "BeverageToppingRO{" +
                "toppingName='" + toppingName + '\'' +
                '}';
    }
}
