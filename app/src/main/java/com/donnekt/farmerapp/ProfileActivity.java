package com.donnekt.farmerapp;

import android.content.Intent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.donnekt.farmerapp.farmer.Farmer;

public class ProfileActivity extends AppCompatActivity {

    TextView topTitle, tvFirstName, tvLastName,  tvPhone, tvNationalId, tvLandArea, buttonEditProfile, viewDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        topTitle = findViewById(R.id.tvTopTitle);
        tvLastName = findViewById(R.id.tvLastName);
        tvFirstName = findViewById(R.id.tvFirstName);
        tvPhone = findViewById(R.id.tvPhone);
        tvNationalId = findViewById(R.id.tvNationalId);
        tvLandArea = findViewById(R.id.tvLandArea);
        viewDashboard = findViewById(R.id.viewDashboard);
        buttonEditProfile = findViewById(R.id.buttonEditProfile);

        Farmer farmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();
        String farmerId = String.valueOf(farmer.getFarmerId());

        topTitle.setText("Farmer Profile");
        tvFirstName.setText("FirstName: "+farmer.getFirstname());
        tvLastName.setText("LastName: "+farmer.getLastname());
        tvPhone.setText("Telephone: "+farmer.getTelephone());
        tvNationalId.setText("N.I.D: "+farmer.getNationalId());
        tvLandArea.setText("Land: "+farmer.getArea()+" m.");

        String FNM= String.valueOf(farmer.getFirstname());
        String LNM = String.valueOf(farmer.getLastname());
        String PHN= String.valueOf(farmer.getTelephone());
        String NID= String.valueOf(farmer.getNationalId());
        String ARE= String.valueOf(farmer.getArea());
        String PWD= String.valueOf(farmer.getPassword());

        // UPDATE SHIT
        buttonEditProfile.setOnClickListener(view -> {
            Intent g = new Intent(getApplicationContext(), FarmerUpdate.class);
            g.putExtra("FNM_key", FNM);
            g.putExtra("LNM_key", LNM);
            g.putExtra("PHN_key", PHN);
            g.putExtra("NID_key", NID);
            g.putExtra("ARE_key", ARE);
            g.putExtra("PWD_key", PWD);
            startActivity(g);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // For killing activity
        });

        viewDashboard.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
        });
    }
}