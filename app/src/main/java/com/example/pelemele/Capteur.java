package com.example.pelemele;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class Capteur extends AppCompatActivity{

    // The sensor manager
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Switch s;
    private Sensor sensor;
    private Sensor sensorMag;
    private Class c;
    private Canvas canvas;
    private Context context;
    private AttributeSet at;
    private Paint paint;
    private Vector3f vA;
    private Vector3f vM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMag = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        s = (Switch) findViewById(R.id.switch1);

        //myView v = new myView(context, at);

//        sensorEventListener = new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                float x = event.values[0];
//                float y = event.values[1];
//                Log.i("Sensor Accelerometer x ", "" + x);
//
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
 //           }
 //       };
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                    float x = event.values[0];
                    vA = new Vector3f(event.values);
                    Log.i("Sensor TYPE_ACCELEROMETER x ", "" + x);
                    Changement(vA);
                }else if(sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
                    float x = event.values[0];
                    vM = new Vector3f(event.values);
                    Log.i("Sensor TYPE_MAGNETIC_FIELD x ", "" + x);
                    Changement(vM);
                }

            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };
        register();
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    s.setText("Sensor off");
                    sensorManager.unregisterListener(sensorEventListener);
                } else {
                    s.setText("Sensor on");
                    register();
                    // The toggle is disabled
                }
            }

        });

        }

    private void Changement(Vector3f v) {
        myView m = findViewById(R.id.m_view);
        m.setV(v);
        m.invalidate();
    }

    ;

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Switch s = (Switch) findViewById(R.id.switch1);
        if (s.isChecked()){
            s.setText("Sensor off");
        }else{
            register();
        }


    }

    private void register(){
        sensorManager.registerListener(sensorEventListener, sensor,10000000);
        sensorManager.registerListener(sensorEventListener, sensorMag,10000000);
    }

}