package com.example.androidproject;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PhoneDetails extends AppCompatActivity {
Button bt;
Button bt1;
Button bt2;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_details);

        bt = findViewById(R.id.button3);
        bt1 = findViewById(R.id.button4);
        bt2 = findViewById(R.id.button5);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Android version release
                String androidVersion = Build.VERSION.RELEASE;

                // Display Android version release
                Toast.makeText(PhoneDetails.this, "Android Version: " + androidVersion, Toast.LENGTH_LONG).show();
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Android version release
                String mfg = Build.MANUFACTURER;

                // Display Android version release
                Toast.makeText(PhoneDetails.this, "Android Manufacturer: " + mfg, Toast.LENGTH_LONG).show();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Android version release
                String model = Build.MODEL;

                // Display Android version release
                Toast.makeText(PhoneDetails.this, "Android Version: " + model, Toast.LENGTH_LONG).show();
            }
        });
    }
}
