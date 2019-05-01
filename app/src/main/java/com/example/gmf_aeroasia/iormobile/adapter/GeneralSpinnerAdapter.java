package com.example.gmf_aeroasia.iormobile.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gmf_aeroasia.iormobile.R;

import java.util.List;

public class GeneralSpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    private LayoutInflater layoutInflater;
    private int resource;
    private List<String> data;


    public GeneralSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List data) {
        super(context, resource, 0,data);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.data = data;
    }



    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    public View createItemView(int position, View convertView, ViewGroup parent){
        final View view = layoutInflater.inflate(resource, parent, false);
        TextView text = view.findViewById(R.id.tv_spinner);
        text.setText(data.get(position));
        return view;
    }
}
