package com.example.pelemele;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class Capteur extends AppCompatActivity {

    // The sensor manager
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capteur);
        Capteur.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Switch s = (Switch) findViewById(R.id.switch1);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listSensor();
                } else {
                    // The toggle is disabled
                }
            }
        });

        };

    private void listSensor() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // La chaîne descriptive de chaque capteur
        StringBuffer sensorDesc = new StringBuffer();
        // pour chaque capteur trouvé, construire sa chaîne descriptive
            sensorDesc.append("New sensor detected : \r\n");
            sensorDesc.append("\tName: " + sensor.getName() + "\r\n");
            sensorDesc.append("\tType: " + getType(sensor.getType()) + "\r\n");
            sensorDesc.append("Version: " + sensor.getVersion() + "\r\n");
            sensorDesc.append("Resolution (in the sensor unit): " + sensor.getResolution() + "\r\n");
            sensorDesc.append("Power in mA used by this sensor while in use" + sensor.getPower() + "\r\n");
            sensorDesc.append("Vendor: " + sensor.getVendor() + "\r\n");
            sensorDesc.append("Maximum range of the sensor in the sensor's unit." + sensor.getMaximumRange() + "\r\n");
            sensorDesc.append("Minimum delay allowed between two events in microsecond" + " or zero if this sensor only returns a value when the data it's measuring changes" + sensor.getMinDelay() + "\r\n");

        // Faire quelque chose de cette liste
        Toast.makeText(this, sensorDesc.toString(), Toast.LENGTH_LONG).show();

        // Pour trouver un capteur spécifique :
        Sensor gyroscopeDefault = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // Pour trouver tous les capteurs d'un type fixé :
        List<Sensor> gyroscopes = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
    }
    private String getType(int type) {
        String strType;
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER: strType = "TYPE_ACCELEROMETER";break;
           // case Sensor.TYPE_GRAVITY:strType = "TYPE_GRAVITY";break;
            // case Sensor.TYPE_GYROSCOPE:    strType = "TYPE_GYROSCOPE";    break;
            //case Sensor.TYPE_LIGHT:strType = "TYPE_LIGHT";break;
            //case Sensor.TYPE_LINEAR_ACCELERATION:strType = "TYPE_LINEAR_ACCELERATION";
            //    break;
            //case Sensor.TYPE_MAGNETIC_FIELD:strType = "TYPE_MAGNETIC_FIELD";break;
            //case Sensor.TYPE_ORIENTATION:strType = "TYPE_ORIENTATION";break;
            //case Sensor.TYPE_PRESSURE:strType = "TYPE_PRESSURE";break;
            //case Sensor.TYPE_PROXIMITY:    strType = "TYPE_PROXIMITY";    break;
            //case Sensor.TYPE_ROTATION_VECTOR:    strType = "TYPE_ROTATION_VECTOR";break;
            //case Sensor.TYPE_TEMPERATURE:strType = "TYPE_TEMPERATURE";break;
            default: strType = "TYPE_UNKNOW";break;
        }
        return strType;
    }
}