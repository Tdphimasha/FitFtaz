package com.himasha.fitfatz;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Gps_Tracking extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private TextView locationTextView;
    private TextView speedTextView;
    private TextView distanceTextView;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private Location previousLocation;
    private float totalDistance;

    Button showMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_tracking);

        // Initialize member variables
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        locationTextView = findViewById(R.id.locationTextView);
        speedTextView = findViewById(R.id.speedTextView);
        distanceTextView = findViewById(R.id.distanceTextView);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (previousLocation != null) {
                    float distance = previousLocation.distanceTo(location);
                    totalDistance += distance;
                }
                previousLocation = location;

                locationTextView.setText("Location: " + location.getLatitude() + ", " + location.getLongitude());
                speedTextView.setText("Speed: " + location.getSpeed() + " m/s");
                distanceTextView.setText("Distance: " + String.format("%.2f", totalDistance / 1000) + " km");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            startLocationUpdates();
        }
        // Set button click listeners
        startButton.setOnClickListener(v -> startLocationUpdates());

        stopButton.setOnClickListener(v -> stopLocationUpdates());

        showMap = findViewById(R.id.showMap);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Gps_Tracking.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
    private void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
        }
    }
}
