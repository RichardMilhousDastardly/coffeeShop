

package com.crucifix.software.coffeeshop.model.reference;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.List;

public class BeverageOption implements Serializable {

    private static final long serialVersionUID = -1;

    private String id;

    @PropertyName("BeverageType")
    private String beverageType;

    @PropertyName("MaxOptions")
    private int maxOptions;

    @PropertyName("Options")
    private List<String> options;

    public BeverageOption() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getBeverageType() {
        return beverageType;
    }

    public void setBeverageType(final String beverageType) {
        this.beverageType = beverageType;
    }

    public int getMaxOptions() {
        return maxOptions;
    }

    public void setMaxOptions(final int maxOptions) {
        this.maxOptions = maxOptions;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(final List<String> options) {
        this.options = options;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BeverageOption that = (BeverageOption) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BeverageOption{" +
                "id='" + id + '\'' +
                ", beverageType='" + beverageType + '\'' +
                ", maxOptions=" + maxOptions +
                ", options=" + options +
                '}';
    }
}
