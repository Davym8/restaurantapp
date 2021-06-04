package com.example.restaurantapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button addrestaurant, showrestaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addrestaurant = findViewById(R.id.addrest);
        showrestaurants = findViewById(R.id.showrest);
    }
    public void addrestaurant(View view)
    {
        Intent intent = new Intent(MainActivity.this, AddPlaceActivity.class);
        startActivity(intent);
    }
    public void showallrestaurants(View view)
    {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}
