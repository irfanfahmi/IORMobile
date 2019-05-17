package com.example.gmf_aeroasia.iormobile.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class SubCategory extends RealmObject {

    @PrimaryKey
    public String cat_sub_id;
    public String cat_id;
    public String cat_sub_desc;

    public String getCat_sub_id() {
        return cat_sub_id;
    }

    public void setCat_sub_id(String cat_sub_id) {
        this.cat_sub_id = cat_sub_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_sub_desc() {
        return cat_sub_desc;
    }

    public void setCat_sub_desc(String cat_sub_desc) {
        this.cat_sub_desc = cat_sub_desc;
    }

    public static RealmResults<SubCategory> getSubCategoryById(Realm realm, String id){
        RealmQuery<SubCategory> query = realm.where(SubCategory.class);
        return query.equalTo("cat_id", id).findAll();
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "cat_sub_id='" + cat_sub_id + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", cat_sub_desc='" + cat_sub_desc + '\'' +
                '}';
    }
}
