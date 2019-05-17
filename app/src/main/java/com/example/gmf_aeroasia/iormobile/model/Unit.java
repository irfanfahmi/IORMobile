package com.example.gmf_aeroasia.iormobile.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Unit extends RealmObject {

    @PrimaryKey
    public String id;
    public String unit;
    public String group;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public static RealmResults<Unit> getAllUnit(Realm realm){
        RealmQuery<Unit> query = realm.where(Unit.class);
        return query.findAll();
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id='" + id + '\'' +
                ", unit='" + unit + '\'' +
                ", group='" + group + '\'' +
                '}';
    }


}
