package com.example.pelemele;

import android.content.Intent;
import android.icu.text.DateIntervalFormat;
import android.icu.text.DateIntervalInfo;
import android.icu.util.DateInterval;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

public class ChronometreActivity extends AppCompatActivity {
    private int MINUTES_PER_HOUR = 60;
    private int SECONDS_PER_MINUTE = 60;
    private int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometre);
        Toast.makeText(ChronometreActivity.this, "La nouvelle activité c'est bien lancée", Toast.LENGTH_SHORT).show();
        Button start = (Button) findViewById(R.id.btn_start);
        Button stop = (Button) findViewById(R.id.btn_stop);
        stop.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                start.setEnabled(false); //desactive le btn start
                stop.setEnabled(true); //active le btn stop
                LocalDateTime d1 = LocalDateTime.now(); //récupère l'heure now();
                String t1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
                    Toast.makeText(ChronometreActivity.this, "Début du chronomètre : " + t1, Toast.LENGTH_SHORT).show();
                    stop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            start.setEnabled(true);//active le btn start
                            stop.setEnabled(false);//desactive le btn stop
                            //String t2 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                            LocalDateTime d2 = LocalDateTime.now(); //récupère l'heure now
                            long time[] = getTime(d1, d2); //fait la différence entre l'heure now() précédente et l'herue now()
                                Toast.makeText(ChronometreActivity.this, "Le chronomètre à durée : " + time[0] + " heures " + time[1] + " minutes " + time[2] + " secondes " , Toast.LENGTH_SHORT).show();


                        }
                    });

            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }
}