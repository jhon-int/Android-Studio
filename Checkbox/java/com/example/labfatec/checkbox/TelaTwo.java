package com.example.labfatec.checkbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by LabFatec on 22/09/2018.
 */

public class TelaTwo extends Activity {

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telatwo);

        Intent parametro = getIntent();
        boolean checkbox =  parametro.getBooleanExtra("checkbox", false);


        String planetas = parametro.getStringExtra("nomePlanetas");

        ImageView imagem = (ImageView) findViewById(R.id.imagem);

        TextView txtPlanetas = (TextView) findViewById(R.id.txtPlanetas);

        if (planetas.contentEquals("Marte")) {
            if (checkbox) {
                imagem.setImageResource(R.drawable.marte);
            }
            txtPlanetas.setText(planetas);

        } else if (planetas.contentEquals("Jupiter")) {
            if (checkbox) {
                imagem.setImageResource(R.drawable.jupiter);
            }
            txtPlanetas.setText(planetas);
        }

    }
}
