package com.example.androidproject;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Storage extends AppCompatActivity {
    Button bt, bt1, bt2;
    private static final int REQUEST_READ_CALL_LOG = 1;
    private TextView reportTextView;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        bt = findViewById(R.id.button6);
        bt1 = findViewById(R.id.button11);
        bt2 = findViewById(R.id.button12);
        reportTextView = findViewById(R.id.textView5);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, REQUEST_READ_CALL_LOG);
                } else {
                    displayCallLogs();
                }
            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String internalStorageInfo = getStorageInfo(getFilesDir(), "Internal Storage");
                reportTextView.setText(internalStorageInfo);
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String externalStorageInfo = getStorageInfo(getExternalFilesDir(null), "External Storage");
                reportTextView.setText(externalStorageInfo);
            }
        });
    }

    private void displayCallLogs() {
        StringBuilder reportBuilder = new StringBuilder();
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            int numberIndex = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIndex = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateIndex = cursor.getColumnIndex(CallLog.Calls.DATE);
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);

            if (numberIndex >= 0 && typeIndex >= 0 && dateIndex >= 0 && durationIndex >= 0) {
                while (cursor.moveToNext()) {
                    String number = cursor.getString(numberIndex);
                    String type = cursor.getString(typeIndex);
                    String date = cursor.getString(dateIndex);
                    String duration = cursor.getString(durationIndex);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String dateString = dateFormat.format(new Date(Long.parseLong(date)));

                    // Process the retrieved data
                    reportBuilder.append("Number: ").append(number).append("\n");
                    reportBuilder.append("Type: ").append(type).append("\n");
                    reportBuilder.append("Date: ").append(dateString).append("\n");
                    reportBuilder.append("Duration: ").append(duration).append("s\n\n");
                }
            } else {
//                Log.e("Cursor Error", "One or more columns not found");
                  Toast.makeText(this, "One or more columns not found", Toast.LENGTH_LONG).show();
            }
            cursor.close();
        } else {
            // Handle case when cursor is null
//            Log.e("Cursor Error", "Cursor is null");
            Toast.makeText(this, "Cursor is null", Toast.LENGTH_LONG).show();
        }


        reportTextView.setText(reportBuilder.toString());
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CALL_LOG) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayCallLogs();
            } else {
                Toast.makeText(this, "Permission denied. Cannot access call logs.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getStorageInfo(File directory, String storageType) {
        if (directory != null) {
            long totalSpace = directory.getTotalSpace();
            long freeSpace = directory.getFreeSpace();
            long usedSpace = totalSpace - freeSpace;

            // Convert bytes to MB or GB for better readability
            double totalSpaceMB = totalSpace / (1024.0 * 1024.0);
            double freeSpaceMB = freeSpace / (1024.0 * 1024.0);
            double usedSpaceMB = usedSpace / (1024.0 * 1024.0);

            // Construct storage information string
            String storageInfo = "Storage Type: " + storageType + "\n"
                    + "Total Space: " + String.format("%.2f", totalSpaceMB) + " MB\n"
                    + "Used Space: " + String.format("%.2f", usedSpaceMB) + " MB\n"
                    + "Free Space: " + String.format("%.2f", freeSpaceMB) + " MB";

            return storageInfo;
        } else {
            return "Failed to access " + storageType;
        }
    }
}
