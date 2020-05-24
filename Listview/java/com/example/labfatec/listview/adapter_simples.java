package com.example.labfatec.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * Created by LabFatec on 27/10/2018.
 */

public class adapter_simples extends BaseAdapter {
    private String[] planetas = new String[]{"Mercurio", "Venus", "Terra", "Marte"};
    private Context context;

    public adapter_simples(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return planetas.length;
    }

    @Override
    public Object getItem(int position) {
        return planetas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        String planeta = planetas[position];
        view = LayoutInflater.from(context).inflate(R.layout.adapter_simples, parent, false);
        TextView t = (TextView) view.findViewById(R.id.text);
        t.setText(planeta);
        return view;
    }
}
