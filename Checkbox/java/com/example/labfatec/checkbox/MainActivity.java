package com.example.labfatec.checkbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener((View.OnClickListener) this);
    }

    public ArrayList retornaPlanetas() {
        ArrayList planetas = new ArrayList();

        planetas.add("Marte");
        planetas.add("Jupiter");

        return planetas;
    }

    @Override
    public void onClick(View view) {

        EditText txtPlanetas=(EditText) findViewById(R.id.txtPlanetas);
        String planetas = txtPlanetas.getText().toString();


        CheckBox checkbox = (CheckBox)findViewById(R.id.check);

        ArrayList r_planetas = retornaPlanetas();

        if (r_planetas.contains(planetas)) {

            Intent TelaTwo = new Intent(this, TelaTwo.class);
            TelaTwo.putExtra("checkbox", checkbox.isChecked());
            TelaTwo.putExtra("nomePlanetas", planetas);

            startActivity(TelaTwo);
        }

    }
}
