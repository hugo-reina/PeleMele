package com.example.pelemele;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Locator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

public class MeteoActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        Button b = (Button) findViewById(R.id.btn_LL);

        if (ContextCompat.checkSelfPermission(MeteoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MeteoActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

            }
        });


        String url = "https:openWeather.org/data/2.5/weather?lat=48.692054&lon=6.184417&appid=989e7d21ce359aaf25ac1720bd42241c";
        try {
            InputStream in = new java.net.URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private JSONObject readStream(InputStream in) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        return new JSONObject(sb.toString());
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MeteoActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, " La Longitude est : "+location.getLongitude() + " et la Lattitue est : " + location.getLatitude(), Toast.LENGTH_SHORT).show();
    }

}