package com.example.jobsam.primeiroprojeto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity2 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent params = getIntent();
        String usuario = params.getStringExtra("txtUsuario");

        //Fizemos um mapeamento do componente de imagem
        ImageView imagem = (ImageView)findViewById(R.id.imagem_1);

        if(usuario.equals("Jhonatan")){
            //Colocar a imaem dentro do componente de imagem
            imagem.setImageResource(R.drawable.pedreiro);
        }
        else{
            imagem.setImageResource(R.drawable.gatinho);
        }




        Toast.makeText(this, "Bem-Vindo, " + usuario, Toast.LENGTH_SHORT).show();

    }
}
