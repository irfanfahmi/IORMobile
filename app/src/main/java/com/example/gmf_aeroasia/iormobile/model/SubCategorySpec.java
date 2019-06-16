package com.example.gmf_aeroasia.iormobile.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class SubCategorySpec extends RealmObject {
    @PrimaryKey
    public String cat_sub_spec_id;
    public String cat_sub_id;
    public String cat_sub_spec_desc;

    public String getCat_sub_spec_id() {
        return cat_sub_spec_id;
    }

    public void setCat_sub_spec_id(String cat_sub_spec_id) {
        this.cat_sub_spec_id = cat_sub_spec_id;
    }

    public String getCat_sub_id() {
        return cat_sub_id;
    }

    public void setCat_sub_id(String cat_sub_id) {
        this.cat_sub_id = cat_sub_id;
    }

    public String getCat_sub_spec_desc() {
        return cat_sub_spec_desc;
    }

    public void setCat_sub_spec_desc(String cat_sub_spec_desc) {
        this.cat_sub_spec_desc = cat_sub_spec_desc;
    }

    public static RealmResults<SubCategorySpec> getSubCategorySpecById(Realm realm, String id){
        String mId = id;
        RealmQuery<SubCategorySpec> query = realm.where(SubCategorySpec.class);
        if(id != null){
            return query.equalTo("cat_sub_id", id).findAll();
        }else{
            return query.findAll();
        }
    }

    @Override
    public String toString() {
        return "SubCategorySpec{" +
                "cat_sub_spec_id='" + cat_sub_spec_id + '\'' +
                ", cat_sub_id='" + cat_sub_id + '\'' +
                ", cat_sub_spec_desc='" + cat_sub_spec_desc + '\'' +
                '}';
    }
}
