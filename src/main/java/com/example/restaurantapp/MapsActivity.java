package com.example.restaurantapp;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import com.example.restaurantapp.database.Databasehelper;
import com.example.restaurantapp.loctemplate.Locations;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.restaurantapp.databinding.ActivityMapsBinding;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    Databasehelper databasehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemaps);
        mapFragment.getMapAsync(this);
        databasehelper = new Databasehelper(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        ArrayList<Locations> locations = databasehelper.getLocation();

        for(int index = 0; index < locations.size(); index++)
        {
            double lat = Double.parseDouble(locations.get(index).getLatitude());
            double lon = Double.parseDouble(locations.get(index).getLongitude());
            String locname = locations.get(index).getName();
            LatLng location = new LatLng(lat, lon);
            //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.addMarker(new MarkerOptions().position(location).title(locname).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
        }

    }
}