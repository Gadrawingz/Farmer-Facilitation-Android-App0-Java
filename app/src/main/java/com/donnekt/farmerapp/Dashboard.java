package com.donnekt.farmerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.donnekt.farmerapp.farmer.Farmer;
import com.google.android.material.button.MaterialButton;

public class Dashboard extends AppCompatActivity {

    MaterialButton requestButton;
    ConstraintLayout manageHarvest;
    ImageButton logoutButton, viewMenus;
    TextView userNames, harvestTv, pesticideTv, fertilizerTv, seedTv;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // INITIALIZATION
        requestButton = findViewById(R.id.requestButton);
        manageHarvest = findViewById(R.id.manageHarvest);
        logoutButton = findViewById(R.id.logoutButton);
        userNames = findViewById(R.id.userNames);
        harvestTv = findViewById(R.id.harvestTv);
        pesticideTv = findViewById(R.id.pesticideTv);
        fertilizerTv = findViewById(R.id.fertilizerTv);
        seedTv = findViewById(R.id.seedTv);
        viewMenus = findViewById(R.id.viewMenus);

        // Stay to page
        Farmer farmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();

        userNames.setText(farmer.getFirstname() + " " + farmer.getLastname());
        seedTv.setText("Seeds/10 kgs");
        fertilizerTv.setText("Fert./ 20 kgs");
        pesticideTv.setText("Pest/ 50doses");
        harvestTv.setText("Harvests (10)");
        userNames.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, ProfileActivity.class)));
        viewMenus.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, Menus.class)));
        requestButton.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, Requesting.class)));
        manageHarvest.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, HarvestActivity.class)));
        logoutButton.setOnClickListener(v -> SharedPrefManager.getInstance(getApplicationContext()).logout());
    }
}