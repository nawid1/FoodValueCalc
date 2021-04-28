package com.foodwaste.foodwastevaluetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button profile = findViewById(R.id.profileLink);
        profile.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
             finish();
         }


     });

        Button addFoodItem = findViewById(R.id.addLink);
        addFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, AddActivity.class));
                finish();
            }


        });

    }
}