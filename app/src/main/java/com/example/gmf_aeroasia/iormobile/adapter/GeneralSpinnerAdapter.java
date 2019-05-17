package com.example.gmf_aeroasia.iormobile.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gmf_aeroasia.iormobile.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

public abstract class GeneralSpinnerAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {
    private int child;
    private LayoutInflater layoutInflater;
    private OrderedRealmCollection<T> realmResults;

    public GeneralSpinnerAdapter(Context context, @NonNull OrderedRealmCollection<T> data) {
        this(context, data, R.layout.item_spinner);
    }

    public GeneralSpinnerAdapter(Context context, @NonNull OrderedRealmCollection<T> data, int child) {
        super(data);
        this.child = child;
        this.realmResults = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = layoutInflater.inflate(child, parent, false);
        } else {
            v = convertView;
        }
        ((TextView) v.findViewById(R.id.tv_spinner)).setText(getEntryText(position));
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = layoutInflater.inflate(child, parent, false);
        } else {
            v = convertView;
        }
        ((TextView) v.findViewById(R.id.tv_spinner)).setText(getEntryText(position));
        return v;
    }

    public abstract String getEntryText(int position);

    public OrderedRealmCollection<T> getData() {
        return realmResults;
    }
}
