package com.example.gmf_aeroasia.iormobile.model;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class Severity extends RealmObject {

    @PrimaryKey
    public int severity_id;
    public String severity_q_definition;
    public String severity_meaning;
    public String severity_value;

    public int getSeverity_id() {
        return severity_id;
    }

    public void setSeverity_id(int severity_id) {
        this.severity_id = severity_id;
    }

    public String getSeverity_q_definition() {
        return severity_q_definition;
    }

    public void setSeverity_q_definition(String severity_q_definition) {
        this.severity_q_definition = severity_q_definition;
    }

    public String getSeverity_meaning() {
        return severity_meaning;
    }

    public void setSeverity_meaning(String severity_meaning) {
        this.severity_meaning = severity_meaning;
    }

    public String getSeverity_value() {
        return severity_value;
    }

    public void setSeverity_value(String severity_value) {
        this.severity_value = severity_value;
    }

    public static RealmResults<Severity> getAllSeverity(Realm realm){
        RealmQuery<Severity> query = realm.where(Severity.class);
        return query.findAll();
    }

    @Override
    public String toString() {
        return "Severity{" +
                "severity_id=" + severity_id +
                ", severity_q_definition='" + severity_q_definition + '\'' +
                ", severity_meaning='" + severity_meaning + '\'' +
                ", severity_value='" + severity_value + '\'' +
                '}';
    }
}
