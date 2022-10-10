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
import android.util.Log;
import android.view.View;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xml.sax.Locator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteoActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    private  String url = "https://api.openWeathermap.org/data/2.5/weather?lat=48.692054&lon=6.184417&appid=989e7d21ce359aaf25ac1720bd42241c";
    private  String urlLL = "https://api.openWeathermap.org/data/2.5/weather?";
        //lat=48.692054&lon=6.184417&
    private  String appid = "appid=989e7d21ce359aaf25ac1720bd42241c";

    //private final String appid = "989e7d21ce359aaf25ac1720bd42241c";

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
        ExecutorService service = Executors.newSingleThreadExecutor();
        TextView t = findViewById(R.id.txt_meteo);
        TextView p = findViewById(R.id.txt_ville);


        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    meteo(t,p);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        ExecutorService ser = Executors.newSingleThreadExecutor();
        TextView lat = findViewById(R.id.txt_lat);
        TextView lon = findViewById(R.id.txt_long);
        EditText txtlat = findViewById(R.id.edit_lat);
        EditText txtlon = findViewById(R.id.edit_lon);
        TextView rep = findViewById(R.id.txt_rep);
        TextView rep1 = findViewById(R.id.txt_rep1);
        lat.setText("Inserez une lattitude");
        lon.setText("Inserez une longitude");
        Button val = (Button) findViewById(R.id.btn_valider);
        val.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ser.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            meteoLL(txtlat,txtlon,rep,rep1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private void meteoLL(EditText txtlat, EditText txtlon, TextView rep, TextView rep1) throws IOException, JSONException {


        String lat = txtlat.getText().toString().trim();
        String lon = txtlon.getText().toString().trim();

        if (lat.equals("")){
            rep.setText("Veuillez inserer une lattitude");
        }else{
            if(lon.equals("")){
                rep.setText("Veuillez inserer une longitude");
            }else{
                double lati = Double.parseDouble(lat);
                double  longi = Double.parseDouble(lon);
                String Vurl = urlLL+"lat="+lati+"&lon="+longi+"&"+appid;
                InputStream in = new java.net.URL(Vurl).openStream();
                JSONObject res = readStream(in);
                String a = res.getString("main");
                String b = getNbr(a);
                String [] c = b.split("\\s+");
                int d = Integer.parseInt(c[0]);
                double e = d+ (Integer.parseInt(c[1])/100);
                double temp = e - 273.15;
                rep.setText(res.getString("name"));
                rep1.setText(Math.round(temp*100.0)/100.0 + " Degré");
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("done","done");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void meteo(TextView t, TextView p) throws IOException, JSONException {


            InputStream in = new java.net.URL(url).openStream();
            JSONObject res = readStream(in);
            String a = res.getString("main");
            String b = getNbr(a);
            String [] c = b.split("\\s+");
            int d = Integer.parseInt(c[0]);
            double e = d+ (Integer.parseInt(c[1])/100);
            double temp = e - 273.15;
            p.setText(res.getString("name"));
            String des = res.getString("weather");

            t.setText("Température : " + Math.round(temp*100.0)/100.0  + " Humidité : " + c[9]);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("done","done");
            }
        });
    }

    static String getNbr(String str)
    {
        // Remplacer chaque nombre non numérique par un espace
        str = str.replaceAll("[^\\d]", " ");
        // Supprimer les espaces de début et de fin
        str = str.trim();
        // Remplacez les espaces consécutifs par un seul espace
        str = str.replaceAll(" +", " ");

        return str;
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 5, MeteoActivity.this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this, " La Longitude est : "+location.getLongitude() + " et la Lattitue est : " + location.getLatitude(), Toast.LENGTH_SHORT).show();
    }

}