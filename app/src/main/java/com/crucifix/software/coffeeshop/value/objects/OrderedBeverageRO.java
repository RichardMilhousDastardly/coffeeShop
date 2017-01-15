package com.crucifix.software.coffeeshop.value.objects;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class OrderedBeverageRO extends RealmObject {

    private String beverageId;
    private String beverageName;
    private String beverageType;
    private RealmList<BeverageToppingRO> beverageToppings = new RealmList<>();
    private RealmList<CoffeeBooleanRO> selected = new RealmList<>();

    public String getBeverageId() {
        return beverageId;
    }

    public void setBeverageId(final String beverageId) {
        this.beverageId = beverageId;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public void setBeverageName(final String beverageName) {
        this.beverageName = beverageName;
    }

    public String getBeverageType() {
        return beverageType;
    }

    public void setBeverageType(final String beverageType) {
        this.beverageType = beverageType;
    }

    public RealmList<BeverageToppingRO> getBeverageToppings() {
        return beverageToppings;
    }

    public void setBeverageToppings(final RealmList<BeverageToppingRO> beverageToppings) {
        this.beverageToppings.clear();
        this.beverageToppings.addAll(beverageToppings);
    }

    public RealmList<CoffeeBooleanRO> getSelected() {
        return selected;
    }

    public void setSelected(final RealmList<CoffeeBooleanRO> selected) {
        this.selected.clear();
        this.selected.addAll(selected);
    }

    @Override
    public String toString() {
        return "OrderedBeverageRO{" +
                "beverageId='" + beverageId + '\'' +
                ", beverageName='" + beverageName + '\'' +
                ", beverageType='" + beverageType + '\'' +
                ", beverageToppings=" + beverageToppings +
                ", selected=" + selected +
                '}';
    }
}
