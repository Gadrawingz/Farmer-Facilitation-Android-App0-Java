package com.donnekt.farmerapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.farmerapp.farmer.Farmer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.view.View.GONE;
import static com.donnekt.farmerapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.farmerapp.DialogShit.showDamnProgressDialog;

public class Menus extends AppCompatActivity {

    String F_SESSION_ID;
    TextView makeRequest, regHarvest, viewHarvest, viewDashboard, viewProfile, pendingPayment, noPendingPayment, userLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(Menus.this, MainActivity.class));
        }

        makeRequest = findViewById(R.id.make_request);
        regHarvest = findViewById(R.id.register_harvest);
        viewHarvest = findViewById(R.id.view_harvest);
        viewDashboard = findViewById(R.id.view_dashboard);
        viewProfile = findViewById(R.id.view_profile);
        pendingPayment = findViewById(R.id.pending_payment);
        noPendingPayment = findViewById(R.id.no_p_payment);
        userLogout = findViewById(R.id.user_logout);

        Farmer activeFarmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();
        F_SESSION_ID = String.valueOf(activeFarmer.getFarmerId());

        showDamnProgressDialog(this, "Loading...","Getting menus ready...",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.FR_VIEW_REQUEST+F_SESSION_ID,
                response -> {
                    Log.d("string", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            pendingPayment.setVisibility(View.VISIBLE);
                            noPendingPayment.setVisibility(GONE);
                        }
                        if(obj.optString("status").equals("unfetched")){
                            noPendingPayment.setVisibility(View.VISIBLE);
                        }
                        exitDamnProgressDialog();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "No Network!", Toast.LENGTH_LONG).show());
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        pendingPayment.setOnClickListener(v -> startActivity(new Intent(Menus.this, Finalization.class)));

        makeRequest.setOnClickListener(v -> startActivity(new Intent(Menus.this, Requesting.class)));

        regHarvest.setOnClickListener(v -> startActivity(new Intent(Menus.this, HarvestActivity.class)));

        viewHarvest.setOnClickListener(v -> startActivity(new Intent(Menus.this, HarvestViewAll.class)));

        viewDashboard.setOnClickListener(v -> startActivity(new Intent(Menus.this, Dashboard.class)));

        viewProfile.setOnClickListener(v -> startActivity(new Intent(Menus.this, ProfileActivity.class)));

        userLogout.setOnClickListener(v -> { SharedPrefManager.getInstance(getApplicationContext()).logout(); });
    }
}