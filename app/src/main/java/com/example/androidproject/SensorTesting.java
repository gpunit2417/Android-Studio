package com.example.androidproject;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SensorTesting extends AppCompatActivity implements SensorEventListener {

    Button bt7;
    Button bt8;
    Button bt9;
    Button bt10;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor proximity;
    private Sensor light;
    private TextView accelerometerDataTextView;
    private TextView gyroscopeDataTextView;
    private TextView proximityDataTextView;
    private TextView lightDataTextView;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_test);

        bt7 = findViewById(R.id.button7);
        bt8 = findViewById(R.id.button8);
        bt9 = findViewById(R.id.button9);
        bt10 = findViewById(R.id.button10);
        accelerometerDataTextView = findViewById(R.id.textView2);
        gyroscopeDataTextView = findViewById(R.id.textView);
        proximityDataTextView = findViewById(R.id.textView3);
        lightDataTextView = findViewById(R.id.textView4);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if accelerometer sensor is available
                accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                if (accelerometer == null) {
                    Toast.makeText(SensorTesting.this, "Accelerometer sensor is unavailable", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                }
                else{
                    sensorManager.registerListener(SensorTesting.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                if(gyroscope == null){
                    Toast.makeText(SensorTesting.this, "Gyroscope sensor is not available", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    sensorManager.registerListener(SensorTesting.this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                if (proximity == null) {
                    Toast.makeText(SensorTesting.this, "Proximity sensor is unavailable", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    sensorManager.registerListener(SensorTesting.this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                if (light == null) {
                    Toast.makeText(SensorTesting.this, "Light sensor is unavailable", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    sensorManager.registerListener(SensorTesting.this, light, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register accelerometer sensor listener
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister accelerometer sensor listener to save battery
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0]; // Acceleration along x-axis
            float y = event.values[1]; // Acceleration along y-axis
            float z = event.values[2]; // Acceleration along z-axis

            // Display accelerometer data
            String accelerometerData = "Accelerometer Data:\nX: " + x + "\nY: " + y + "\nZ: " + z;
            accelerometerDataTextView.setText(accelerometerData);
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0]; // Angular speed around x-axis
            float y = event.values[1]; // Angular speed around y-axis
            float z = event.values[2]; // Angular speed around z-axis

            // Display gyroscope data
            String gyroscopeData = "Gyroscope Data:\nX: " + x + "\nY: " + y + "\nZ: " + z;
            gyroscopeDataTextView.setText(gyroscopeData);
        }
        else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            float x = event.values[0];
            String proximityData = "Proximity Data:\n" + x;
            proximityDataTextView.setText(proximityData);
        }
        else if (event.sensor.getType() == Sensor.TYPE_LIGHT){
            float x = event.values[0];
            String lightData = "Light Data:\n" + x;
            lightDataTextView.setText(lightData);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
