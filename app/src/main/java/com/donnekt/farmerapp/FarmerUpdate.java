package com.donnekt.farmerapp;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.farmerapp.farmer.Farmer;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FarmerUpdate extends AppCompatActivity {

    EditText editTextFName, editTextLName, editTextNID, editTextPhone, editTextLandArea, editTextPassword;
    ProgressBar loadingProgBar;
    TextView registerButton, closeButton;
    String F_SESSION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_update);

        Farmer activeFarmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();
        F_SESSION_ID = String.valueOf(activeFarmer.getFarmerId());

        loadingProgBar = findViewById(R.id.loadingProgress);
        editTextFName = findViewById(R.id.editTextFName);
        editTextLName = findViewById(R.id.editTextLName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNID = findViewById(R.id.editTextNID);
        editTextLandArea = findViewById(R.id.editTextLandArea);
        editTextPassword = findViewById(R.id.editTextPassword);

        registerButton = findViewById(R.id.registerButton);
        closeButton = findViewById(R.id.closeButton);

        editTextFName.setText(getIntent().getStringExtra("FNM_key"));
        editTextLName.setText(getIntent().getStringExtra("LNM_key"));
        editTextPhone.setText(getIntent().getStringExtra("PHN_key"));
        editTextNID.setText(getIntent().getStringExtra("NID_key"));
        editTextLandArea.setText(getIntent().getStringExtra("ARE_key"));
        editTextPassword.setText(getIntent().getStringExtra("PWD_key"));

        // Do some shit @gadrawingz
        registerButton.setOnClickListener(view -> updateData());
        closeButton.setOnClickListener(v -> startActivity(new Intent(FarmerUpdate.this, ProfileActivity.class)));
    }

    // my real quick function
    private void updateData() {
        final String fFName = editTextFName.getText().toString().trim();
        final String fLName = editTextLName.getText().toString().trim();
        final String fNatId = editTextNID.getText().toString().trim();
        final String fPhone = editTextPhone.getText().toString().trim();
        final String fPass = editTextPassword.getText().toString().trim();
        final String fArea = editTextLandArea.getText().toString().trim();

        //first we will do the validations
        if (TextUtils.isEmpty(fFName)) {
            editTextFName.setError("Please enter firstname");
            editTextFName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(fLName)) {
            editTextFName.setError("Please enter lastname");
            editTextFName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(fPhone)) {
            editTextPhone.setError("Please enter telephone");
            editTextPhone.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(fNatId)) {
            editTextNID.setError("Please enter your National ID");
            editTextNID.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(fArea)) {
            editTextLandArea.setError("Please enter your Area in meters");
            editTextLandArea.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(fPass)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }

        // Finalize operation!
        transferDataByVolley(fFName, fLName, fPhone, fNatId, fPass, fArea);
    }

    private void transferDataByVolley(String FNM, String LNM, String PHN, String NID, String PWD, String ARE) {
        loadingProgBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FARMER_UPDATE+F_SESSION_ID, response -> {
            loadingProgBar.setVisibility(View.GONE);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
                Toast.makeText(FarmerUpdate.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                startActivity(new Intent(FarmerUpdate.this, MainActivity.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(FarmerUpdate.this, "Network error!", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", FNM);
                params.put("lastname", LNM);
                params.put("telephone", PHN);
                params.put("national_id", NID);
                params.put("password", PWD);
                params.put("land_area", ARE);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}