package com.example.jobsam.primeiroprojeto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btOk = (Button) findViewById(R.id.btOk);
        btOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText txtUsuario = findViewById(R.id.txtUsuario);
        String usuario = txtUsuario.getText().toString();

        EditText txtSenha = findViewById(R.id.txtSenha);
        String senha = txtSenha.getText().toString();

        if (usuario.equals("Jhonatan") || usuario.equals("Rafa") && senha.equals("123")) {
            Toast.makeText(this, "Acesso Autorizado ", Toast.LENGTH_SHORT).show();

            Intent tela2 = new Intent(this, MainActivity2.class);
            tela2.putExtra("txtUsuario", usuario);
            startActivity(tela2);
        }
        else{
            Toast.makeText(this, "Acesso Negado ", Toast.LENGTH_SHORT).show();
        }


    }
}
