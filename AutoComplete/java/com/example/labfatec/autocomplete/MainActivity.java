package com.example.labfatec.autocomplete;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity {

    public static final String[] ESTADOS = new String[]{"Acre", "Alagoas", "Amapá", "Amazonas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoCompleteTextView estados = (AutoCompleteTextView) findViewById(R.id.estados);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ESTADOS);
        estados.setAdapter(adaptador);
    }
}
