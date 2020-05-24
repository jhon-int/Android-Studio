package com.example.labfatec.listview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    protected static final String TAG = "livro";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new adapter_simples(this));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int idx, long id) {
       String s = (String) parent.getAdapter().getItem(idx);
        Toast.makeText(this, "Texto selecionado: " + s + ", posição: " + idx, Toast.LENGTH_SHORT).show();
    }
}
