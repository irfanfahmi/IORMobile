package com.example.gmf_aeroasia.iormobile.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Probability extends RealmObject {

    @PrimaryKey
    public int probability_id;
    public String probability_a_definition;
    public String probability_meaning;
    public String probability_value;

    public int getProbability_id() {
        return probability_id;
    }

    public void setProbability_id(int probability_id) {
        this.probability_id = probability_id;
    }

    public String getProbability_a_definition() {
        return probability_a_definition;
    }

    public void setProbability_a_definition(String probability_a_definition) {
        this.probability_a_definition = probability_a_definition;
    }

    public String getProbability_meaning() {
        return probability_meaning;
    }

    public void setProbability_meaning(String probability_meaning) {
        this.probability_meaning = probability_meaning;
    }

    public String getProbability_value() {
        return probability_value;
    }

    public void setProbability_value(String probability_value) {
        this.probability_value = probability_value;
    }

    public static RealmResults<Probability> getAllProbability(Realm realm){
        RealmQuery<Probability> query = realm.where(Probability.class);
        return query.findAll();
    }


    @Override
    public String toString() {
        return "Probability{" +
                "probability_id=" + probability_id +
                ", probability_a_definition='" + probability_a_definition + '\'' +
                ", probability_meaning='" + probability_meaning + '\'' +
                ", probability_value='" + probability_value + '\'' +
                '}';
    }
}
