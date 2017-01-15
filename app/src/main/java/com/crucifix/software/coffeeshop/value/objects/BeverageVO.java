package com.crucifix.software.coffeeshop.value.objects;

import java.util.ArrayList;
import java.util.List;

public class BeverageVO {

    private final String id;
    private final String beverageName;
    private final String beverageType;
    private final double beveragePrice;
    private final List<BeverageToppingVO> beverageToppings = new ArrayList<>();
    private boolean[] selected;
    private double currentOptionCost = 0;
    private double previousOptionCost = 0;

    private int quantity;

    public BeverageVO(final BeverageVOBuilder beverageVOBuilder) {
        this.id = beverageVOBuilder.getId();
        this.beverageName = beverageVOBuilder.getBeverageName();
        this.beverageType = beverageVOBuilder.getBeverageType();
        this.beveragePrice = beverageVOBuilder.beveragePrice;
        this.beverageToppings.clear();
        this.beverageToppings.addAll(beverageVOBuilder.getBeverageToppings());
        this.quantity = beverageVOBuilder.getQuantity();
    }

    public String getId() {
        return id;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public String getBeverageType() {
        return beverageType;
    }

    public double getBeveragePrice() {
        return beveragePrice;
    }

    public List<BeverageToppingVO> getBeverageToppings() {
        return beverageToppings;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public boolean[] getSelected() {
        return selected;
    }

    public void setSelected(final boolean[] selected) {
        this.selected = selected;
    }

    public double getCurrentOptionCost() {
        return currentOptionCost;
    }

    public void setCurrentOptionCost(final double currentOptionCost) {
        this.currentOptionCost = currentOptionCost;
    }

    public double getPreviousOptionCost() {
        return previousOptionCost;
    }

    public void setPreviousOptionCost(final double previousOptionCost) {
        this.previousOptionCost = previousOptionCost;
    }

    @Override
    public String toString() {
        return "BeverageVO{" +
                "id='" + id + '\'' +
                ", beverageName='" + beverageName + '\'' +
                ", beverageType='" + beverageType + '\'' +
                ", beveragePrice=" + beveragePrice +
                ", beverageToppings=" + beverageToppings +
                ", quantity=" + quantity +
                '}';
    }

    /**
     * Builder
     */
    public static class BeverageVOBuilder {

        private String id;
        private String beverageName;
        private String beverageType;
        private double beveragePrice;
        private List<BeverageToppingVO> beverageToppings = new ArrayList<>();
        private int quantity;

        public BeverageVOBuilder id(final String id) {
            this.id = id;
            return this;
        }

        public BeverageVOBuilder beverageName(final String beverageName) {
            this.beverageName = beverageName;
            return this;
        }

        public BeverageVOBuilder beverageType(final String beverageType) {
            this.beverageType = beverageType;
            return this;
        }

        public BeverageVOBuilder beveragePrice(final double beveragePrice) {
            this.beveragePrice = beveragePrice;
            return this;
        }

        public BeverageVOBuilder beverageToppings(final List<BeverageToppingVO> beverageToppings) {
            this.beverageToppings.clear();
            this.beverageToppings.addAll(beverageToppings);
            return this;
        }

        public BeverageVOBuilder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public BeverageVO build() {
            return new BeverageVO(this);
        }

        public String getId() {
            return id;
        }

        public String getBeverageName() {
            return beverageName;
        }

        public String getBeverageType() {
            return beverageType;
        }

        public double getBeveragePrice() {
            return beveragePrice;
        }

        public List<BeverageToppingVO> getBeverageToppings() {
            return beverageToppings;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
