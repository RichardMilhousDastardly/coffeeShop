package com.crucifix.software.coffeeshop.value.objects;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SubmittedOrderRO extends RealmObject {

    @PrimaryKey
    private String realmId = UUID.randomUUID().toString();

    private Date createdTimestamp;

    private RealmList<OrderedBeverageRO> orderedBeverageList = new RealmList<>();

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(final String realmId) {
        this.realmId = realmId;
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(final Date createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public RealmList<OrderedBeverageRO> getOrderedBeverageList() {
        return orderedBeverageList;
    }

    public void setOrderedBeverageList(final RealmList<OrderedBeverageRO> orderedBeverageList) {
        this.orderedBeverageList.clear();
        this.orderedBeverageList.addAll(orderedBeverageList);
    }

    @Override
    public String toString() {
        return "SubmittedOrderRO{" +
                "realmId='" + realmId + '\'' +
                ", createdTimestamp=" + createdTimestamp +
                ", orderedBeverageList=" + orderedBeverageList +
                '}';
    }
}
