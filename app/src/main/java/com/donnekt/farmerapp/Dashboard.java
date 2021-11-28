package com.donnekt.farmerapp;

import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.RequestQueue;
import com.google.android.material.button.MaterialButton;

public class Dashboard extends AppCompatActivity {

    public TextView createAccount, buttonLogin;
    MaterialButton requestButton;
    ProgressBar loginLoadingPB;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // INITIALIZATION
        requestButton = findViewById(R.id.requestButton);
        requestButton.setOnClickListener(v -> startActivity(new Intent(Dashboard.this, Requesting.class)));

    }
}