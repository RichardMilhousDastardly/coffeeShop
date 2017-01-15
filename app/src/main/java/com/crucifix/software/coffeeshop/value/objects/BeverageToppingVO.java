package com.crucifix.software.coffeeshop.value.objects;

public class BeverageToppingVO {

    private final String toppingName;
    private final double toppingPrice;

    public BeverageToppingVO(final BeverageToppingVOBuilder beverageToppingVOBuilder) {
        this.toppingName = beverageToppingVOBuilder.getToppingName();
        this.toppingPrice = beverageToppingVOBuilder.getToppingPrice();
    }

    public String getToppingName() {
        return this.toppingName;
    }

    public double getToppingPrice() {
        return this.toppingPrice;
    }

    public static class BeverageToppingVOBuilder {

        private String toppingName;
        private double toppingPrice;

        public BeverageToppingVOBuilder() {
        }

        public BeverageToppingVOBuilder toppingName(final String toppingName) {
            this.toppingName = toppingName;
            return this;
        }

        public BeverageToppingVOBuilder toppingPrice(final double toppingPrice) {
            this.toppingPrice = toppingPrice;
            return this;
        }

        public String getToppingName() {
            return toppingName;
        }

        public double getToppingPrice() {
            return toppingPrice;
        }

        public BeverageToppingVO build() {
            return new BeverageToppingVO(this);
        }


    }

}
