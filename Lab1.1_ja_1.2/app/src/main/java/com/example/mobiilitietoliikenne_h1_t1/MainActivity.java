package com.example.mobiilitietoliikenne_h1_t1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView gpsLocation;
    private TextView gpsCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.Button_UpdateGPS).setOnClickListener(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        gpsLocation = findViewById(R.id.GPS_Location);
        gpsCity = findViewById(R.id.GPS_City);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.d("Permission", "not granted1");
            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    100);
        }


    }

    public void getGpsLocation() {
        Log.d("getLocation", "test");
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    String latitude = String.format(Locale.US, "%.2f", lat);
                    String longitude = String.format(Locale.US, "%.2f", lng);
                    Log.d("Location Latitude:", latitude);
                    Log.d("Location Longitude:", longitude);
                    gpsLocation.setText(latitude + ", " + longitude);
                    getCity(getApplicationContext(), lat, lng);
                }
            }
        });
    }

    public void getCity(Context context, double lat, double lng) {
        Geocoder coder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = coder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            Log.d("City:", String.valueOf(obj.getLocality()));
            gpsCity.setText(obj.getLocality() + "\n" + obj.getCountryName());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.Button_UpdateGPS) {
            Log.d("Button", "Location update");
            getGpsLocation();
        }
    }
}
