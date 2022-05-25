package com.donnekt.farmerapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.farmerapp.farmer.Farmer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HarvestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView viewButton, saveButton;
    EditText harvestType, harvestQuantity, harvestPrice, seasonTerm, seasonYear;
    ProgressBar isDataLoading;
    String F_SESSION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest);

        Farmer activeFarmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();
        F_SESSION_ID = String.valueOf(activeFarmer.getFarmerId());

        if(!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(HarvestActivity.this, MainActivity.class));
        }

        // INITIALIZATION
        viewButton = findViewById(R.id.viewButton);
        saveButton = findViewById(R.id.registerButton);
        isDataLoading = findViewById(R.id.loadingProgress);
        harvestType = findViewById(R.id.harvestType);
        harvestQuantity = findViewById(R.id.editTextQuantity);
        harvestPrice = findViewById(R.id.editTextPrice);
        seasonTerm = findViewById(R.id.seasonTerm);
        seasonYear = findViewById(R.id.seasonYear);

        // Stay on good shit bruh!
        /*if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
        }*/

        // Save & View events killInQ
        saveButton.setOnClickListener(this);
        viewButton.setOnClickListener(this);

    }

    private boolean inputsAreCorrect(String type, String quantity, String price, String sYear, String sTerm) {
        if (type.isEmpty()) {
            harvestType.setError("Please enter item name");
            harvestType.requestFocus();
            return false;
        }

        if (quantity.isEmpty()) {
            harvestQuantity.setError("Please enter quantity");
            harvestQuantity.requestFocus();
            return false;
        }

        if (price.isEmpty()) {
            harvestPrice.setError("Please enter unit price");
            harvestPrice.requestFocus();
            return false;
        }

        if (sYear.isEmpty()) {
            seasonYear.setError("Please enter valid season year");
            seasonYear.requestFocus();
            return false;
        }

        if (sTerm.isEmpty()) {
            seasonYear.setError("Please enter valid season term");
            seasonYear.requestFocus();
            return false;
        }
        return true;
    }

    // In this method we will do the create operation
    private void addHarvest() {
        String price = harvestPrice.getText().toString().trim();
        String quantity = harvestQuantity.getText().toString().trim();
        String type = harvestType.getText().toString().trim();
        String sTerm = seasonTerm.getText().toString().trim();
        String sYear = seasonYear.getText().toString().trim();

        // Validating the inputs
        if(inputsAreCorrect(type, quantity, price, sYear, sTerm)) {
            isDataLoading.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.HARVEST_REGISTER,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(HarvestActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            harvestType.getText().clear();
                            harvestPrice.getText().clear();
                            harvestQuantity.getText().clear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("farmer_id", F_SESSION_ID);
                    params.put("item_type", type);
                    params.put("quantity", quantity);
                    params.put("harvest_price", price);
                    params.put("season_year",sYear);
                    params.put("season_term", sTerm);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add Dept
            case R.id.registerButton:
                addHarvest();
                break;

            // case: click to view all
            case R.id.viewButton:
                startActivity(new Intent(this, HarvestViewAll.class));
                break;
        }
    }

}