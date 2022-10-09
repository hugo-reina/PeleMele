package com.example.pelemele;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long);
        ProgressBar b = findViewById(R.id.progressBar);
        b.setVisibility(View.INVISIBLE);

        Button a = (Button) findViewById(R.id.btn_TL);
        a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bar(v);
            };
        });
    };
    public void Bar(View view) {
        ProgressBar b = findViewById(R.id.progressBar);
        b.setVisibility(View.VISIBLE);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                affichage(b);
            }
        });


    }
    private void affichage(ProgressBar b){
        int i = 0;
        int c = 0;
        while (i < 1000000000) {
            c = c + 1;
            i++;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                b.setVisibility(View.INVISIBLE);
                Toast.makeText(LongActivity.this, "Fin du message", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
