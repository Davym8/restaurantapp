package com.example.restaurantapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.example.restaurantapp.database.Databasehelper;
import java.util.Arrays;

public class AddPlaceActivity extends AppCompatActivity
{
    Databasehelper databasehelper;
    LocationManager locationManager;
    LocationListener locationListener;
    AutocompleteSupportFragment autofragment;
    Location savedlocation = new Location("Temp");
    //double latitudes, longitudes;
    EditText restaurantname;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplace);
        //latitudes = 0;
        //longitudes = 0;
        databasehelper = new Databasehelper(this);
        restaurantname = findViewById(R.id.restaurantname);
        Places.initialize(getApplicationContext(), "AIzaSyDxZ3ySUNcU2U5tjPwfYiMlVn7Akn2zAAk");
        PlacesClient placesClient = Places.createClient(this);
        autofragment = (AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(R.id.autofragment);
        autofragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));
        autofragment.setOnPlaceSelectedListener(new PlaceSelectionListener()
        {
            @Override
            public void onPlaceSelected(@NonNull Place place)
            {
                //latitudes =place.getLatLng().latitude;
                //longitudes = place.getLatLng().longitude;
                savedlocation.setLatitude(place.getLatLng().latitude);
                savedlocation.setLongitude(place.getLatLng().longitude);
            }
            @Override
            public void onError(@NonNull Status status)
            {
            }
        });
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(@NonNull Location location)
            {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }
    public void getcurrentposition(View view)
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
            {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        savedlocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        autofragment.setText(savedlocation.getLatitude() + ", " + savedlocation.getLongitude());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Status status = Autocomplete.getStatusFromIntent(data);
        Log.e("filterlogcat", status.toString());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }
    public void showlocation(View view)
    {
        if (savedlocation.getLatitude() != 0 && savedlocation.getLongitude() != 0)
        {
            Intent intent = new Intent(AddPlaceActivity.this, showmapActivity.class);
            intent.putExtra("latt", savedlocation.getLatitude());
            intent.putExtra("long", savedlocation.getLongitude());
            startActivity(intent);
        }
        else
        {
            Toast.makeText(AddPlaceActivity.this, "select location", Toast.LENGTH_LONG).show();
        }
    }
    private void resetdata()
    {
        restaurantname.setText("");
        autofragment.setText("");
        savedlocation = new Location("Temp");
    }
    public void saveplace(View view)
    {
        if (restaurantname.getText().toString().isEmpty())
        {
            Toast.makeText(AddPlaceActivity.this, "Enter location name", Toast.LENGTH_LONG).show();
        }
        if (savedlocation.getLatitude() == 0 && savedlocation.getLongitude() == 0)
        {
            Toast.makeText(AddPlaceActivity.this, "Choose a location", Toast.LENGTH_LONG).show();
        }
        if (!restaurantname.getText().toString().isEmpty())
        {
            com.example.restaurantapp.loctemplate.Locations location = new com.example.restaurantapp.loctemplate.Locations();
            location.setName(restaurantname.getText().toString());
            location.setLatitude("" + savedlocation.getLatitude());
            location.setLongitude("" + savedlocation.getLongitude());

            long result = databasehelper.addLocation(location);

            if (result > 0) {
                Toast.makeText(AddPlaceActivity.this, "Location added", Toast.LENGTH_LONG).show();
                resetdata();
            } else {
                Toast.makeText(AddPlaceActivity.this, "Error: failed to add location", Toast.LENGTH_LONG).show();
            }
        }
    }

}
