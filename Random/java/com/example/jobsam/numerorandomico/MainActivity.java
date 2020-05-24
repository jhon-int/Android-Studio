package com.example.jobsam.numerorandomico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btJogar = (Button) findViewById(R.id.btJogar);
        btJogar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent tela2 = new Intent(this, MainActivity2.class);
        int Random = NumeroRandomico(1,6);
        tela2.putExtra("param-rand",Random);
        startActivity(tela2);
    }

    public int NumeroRandomico (int min, int max) {
        Random rand = new Random();
        int n = rand.nextInt(max)+min;

        return n;
    }


}
