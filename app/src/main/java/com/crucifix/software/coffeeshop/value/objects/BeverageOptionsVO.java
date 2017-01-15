package com.crucifix.software.coffeeshop.value.objects;

import java.util.ArrayList;
import java.util.List;

public class BeverageOptionsVO {

    private final String id;
    private final String beverageType;
    private final int maxSelectableOptions;
    private final List<String> options = new ArrayList<>();

    public BeverageOptionsVO(final BeverageOptionsVOBuilder beverageOptionsVOBuilder) {
        this.id = beverageOptionsVOBuilder.getId();
        this.beverageType = beverageOptionsVOBuilder.getBeverageType();
        this.maxSelectableOptions = beverageOptionsVOBuilder.getMaxSelectableOptions();
        this.options.clear();
        this.options.addAll(beverageOptionsVOBuilder.getOptions());
    }

    public String getId() {
        return id;
    }

    public String getBeverageType() {
        return beverageType;
    }

    public int getMaxSelectableOptions() {
        return maxSelectableOptions;
    }

    public List<String> getOptions() {
        return options;
    }

    public static class BeverageOptionsVOBuilder {

        private String id;
        private String beverageType;
        private int maxSelectableOptions;
        private List<String> options = new ArrayList<>();

        public BeverageOptionsVOBuilder() {
        }

        public BeverageOptionsVOBuilder id(final String id) {
            this.id = id;
            return this;
        }

        public BeverageOptionsVOBuilder beverageType(final String beverageType) {
            this.beverageType = beverageType;
            return this;
        }

        public BeverageOptionsVOBuilder maxSelectableOptions(final int maxSelectableOptions) {
            this.maxSelectableOptions = maxSelectableOptions;
            return this;
        }

        public BeverageOptionsVOBuilder options(final List<String> options) {
            this.options = options;
            return this;
        }

        public BeverageOptionsVO build() {
            return new BeverageOptionsVO(this);
        }

        public String getId() {
            return id;
        }

        public String getBeverageType() {
            return beverageType;
        }

        public int getMaxSelectableOptions() {
            return maxSelectableOptions;
        }

        public List<String> getOptions() {
            return options;
        }
    }


}
