package com.donnekt.farmerapp;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.farmerapp.farmer.Farmer;
import com.donnekt.farmerapp.farmer.Fertilizer;
import com.donnekt.farmerapp.farmer.Pesticide;
import com.donnekt.farmerapp.farmer.Seed;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import static android.view.View.GONE;
import static android.R.layout.simple_spinner_item;
import static com.donnekt.farmerapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.farmerapp.DialogShit.showDamnProgressDialog;

public class Finalization extends AppCompatActivity implements View.OnClickListener {

    String F_SESSION_ID, FINAL_REQ, FINAL_PHONE, FINAL_AMOUNT;
    ProgressBar isDataLoading;
    EditText paymentNo;
    TextView finishButton, amountToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalization);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(Finalization.this, MainActivity.class));
        }

        Farmer activeFarmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();
        F_SESSION_ID = String.valueOf(activeFarmer.getFarmerId());

        isDataLoading = findViewById(R.id.loadingProgress);
        finishButton = findViewById(R.id.finishButton);
        paymentNo = findViewById(R.id.paymentNo);
        amountToPay = findViewById(R.id.amountTotal);
        finishButton.setOnClickListener(this);

        fetchRequestData();

    }

    private void fetchRequestData() {
        showDamnProgressDialog(this, "Loading...","Getting request data...",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.FR_VIEW_REQUEST+F_SESSION_ID,
                response -> {
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){

                            JSONArray dataArray  = obj.getJSONArray("farmer_req");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                String reqId = dataObj.getString("req_id");

                                double sPrice = Integer.parseInt(dataObj.getString("s_price"));
                                int sQuantity = Integer.parseInt(dataObj.getString("seed_qty"));
                                double seedTotal = sPrice * sQuantity;

                                double fPrice = Integer.parseInt(dataObj.getString("f_price"));
                                int fQuantity = Integer.parseInt(dataObj.getString("fert_qty"));
                                double fertTotal = fPrice * fQuantity;

                                double pPrice = Integer.parseInt(dataObj.getString("p_price"));
                                int pQuantity = Integer.parseInt(dataObj.getString("pest_qty"));
                                double pestTotal = pPrice * pQuantity;

                                double mainTotal = (seedTotal + fertTotal + pestTotal);
                                double shortenedAmt = (int)(Math.round(mainTotal * 100))/100.0;

                                FINAL_REQ = dataObj.getString("req_id");
                                FINAL_PHONE= dataObj.getString("telephone");
                                FINAL_AMOUNT= String.valueOf(shortenedAmt);
                                amountToPay.setText(shortenedAmt+" rwf.");
                                exitDamnProgressDialog();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Network problem!", Toast.LENGTH_SHORT).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean correctNumberGiven(String NUM) {
        if (NUM.isEmpty()) {
            paymentNo.setError("Please enter seed quantity");
            paymentNo.requestFocus();
            return false;
        }
        return true;
    }
    private void finishAndSave() {
        String phoneNumber = paymentNo.getText().toString().trim();
        if(correctNumberGiven(phoneNumber)) {
            isDataLoading.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FR_FINALIZATION,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Finalization.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Finalization.this, Dashboard.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Network problem!", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("req_id", FINAL_REQ);
                    params.put("paid_amount", FINAL_AMOUNT);
                    params.put("paid_telno", phoneNumber);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.finishButton) {
            finishAndSave();
        }
    }
}