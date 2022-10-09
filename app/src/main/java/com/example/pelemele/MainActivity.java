package com.example.pelemele;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.btn_bjr);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Bonjour", Toast.LENGTH_SHORT).show();
            }
        });

        Button p = (Button) findViewById(R.id.btn_long);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LongActivity.class);
                startActivity(intent);
            }
        });

        Button h = (Button) findViewById(R.id.btn_contact);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Contact.class);
                startActivity(intent);
            }
        });

        Button g = (Button) findViewById(R.id.btn_capteur);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Capteur.class);
                startActivity(intent);
            }
        });

        Button m = (Button) findViewById(R.id.btn_meteo);
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MeteoActivity.class);
                startActivity(intent);
            }
        });

        Button c = (Button) findViewById(R.id.btn_chrono);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChronometreActivity.class);
                startActivity(intent);
            }
        });
        Log.i("MainActivity","une info");

        Button d = (Button) findViewById(R.id.button);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Voir.class);
                startActivity(intent);
                FileInputStream fis = null;
                try {
                    fis = openFileInput("image.data");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bm = BitmapFactory.decodeStream(fis);
                // Reste à mettre bm à mettre sur l’ImageView
            }
        });

    Button photo = (Button) findViewById(R.id.btn_photo);
        photo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 123);
        }
    });


}
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 123 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Toast.makeText(MainActivity.this, "Hauteur de la photo : " + imageBitmap.getHeight(), Toast.LENGTH_SHORT).show();
            FileOutputStream fos = null;
            try {
                fos = openFileOutput("image.data", MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        Menu m = menu;
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onOptionsItemSelected (MenuItem item) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
            }
        });

        if (item.getItemId() == R.id.items_leave) {
            Toast.makeText(MainActivity.this, "Etes vous sûr de vouloir Quitter l'Application ? (Cliquez à nouveau sur le bouton pour confirmé", Toast.LENGTH_SHORT).show();
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(MainActivity.this, "Vous avez Quitter l'Application", Toast.LENGTH_SHORT).show();
                    finish();
                    return false;
                }
            });
        }

        if (item.getItemId() == R.id.item_chrono) {
            LocalDateTime d1 = LocalDateTime.now();
            String t1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH-mm-ss");
            Toast.makeText(MainActivity.this, "Début du chronomètre : " + t1, Toast.LENGTH_SHORT).show();
            terminerChrono(d1,item);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void terminerChrono(LocalDateTime d1, MenuItem item){
        if (item.getItemId() == R.id.item_fin) {

            LocalDateTime d2 = LocalDateTime.now();
            long time[] = getTime(d1, d2);
            Toast.makeText(MainActivity.this, "Le chronomètre à durée : " + time[0] + " heures " + time[1] + " minutes " + time[2] + " secondes ", Toast.LENGTH_SHORT).show();

        }
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