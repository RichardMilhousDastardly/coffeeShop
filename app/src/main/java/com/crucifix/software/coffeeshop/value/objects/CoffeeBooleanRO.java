package com.crucifix.software.coffeeshop.value.objects;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

@RealmClass
public class CoffeeBooleanRO implements RealmModel {

    private boolean selected;

    public CoffeeBooleanRO() {
    }

    public CoffeeBooleanRO(final boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }
}
