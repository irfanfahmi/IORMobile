package com.example.gmf_aeroasia.iormobile.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject {

    @PrimaryKey
    public String cat_id;
    public String cat_name;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public static RealmResults<Category> getAllCategory(Realm realm){
        RealmQuery<Category> query = realm.where(Category.class);
        return query.findAll();
    }

    @Override
    public String toString() {
        return "Category{" +
                "cat_id='" + cat_id + '\'' +
                ", cat_name='" + cat_name + '\'' +
                '}';
    }
}
