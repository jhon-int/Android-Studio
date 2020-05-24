package com.example.jobsam.numerorandomico;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity2 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent params = getIntent();
        int rand = params.getIntExtra("param-rand", 0);

        TextView txtRand = (TextView) findViewById(R.id.txtNumero);
        txtRand.setText(String.valueOf(rand));

        ImageView imagem = (ImageView)findViewById(R.id.imagem);

        if(rand == 1){
            imagem.setImageResource(R.drawable.dado1);
        }
        else if(rand == 2){
            imagem.setImageResource(R.drawable.dado2);
        }
        else if(rand == 3){
            imagem.setImageResource(R.drawable.dado3);
        }
        else if(rand == 4){
            imagem.setImageResource(R.drawable.dado4);
        }
        else if(rand == 5){
            imagem.setImageResource(R.drawable.dado5);
        }
        else if(rand == 6){
            imagem.setImageResource(R.drawable.dado6);
        }

    }
}
