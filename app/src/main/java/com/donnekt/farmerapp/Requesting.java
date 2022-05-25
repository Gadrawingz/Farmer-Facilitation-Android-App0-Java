package com.donnekt.farmerapp;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import static android.view.View.GONE;
import static android.R.layout.simple_spinner_item;
import static com.donnekt.farmerapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.farmerapp.DialogShit.showDamnProgressDialog;

public class Requesting extends AppCompatActivity implements View.OnClickListener {

    String F_SESSION_ID, F_LAND_AREA;
    Spinner paymentStatus;
    TextView saveButton, farmerLandArea, sMinimum, fMinimum, pMinimum;
    ProgressBar isDataLoading;
    EditText pQuantity, sQuantity, fQuantity, seasonYear, seasonTerm;
    Spinner itemPesticide, itemSeed, itemFertilizer;

    private ArrayList<Seed> seedArrayList;
    private final ArrayList<String> seeds = new ArrayList<>();
    private int selectedSeed;

    private ArrayList<Fertilizer> fertilizerArrayList;
    private final ArrayList<String> fertilizers = new ArrayList<>();
    private int selectedFertilizer;

    private ArrayList<Pesticide> pesticideArrayList;
    private final ArrayList<String> pesticides = new ArrayList<>();
    private int selectedPesticide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requesting);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(Requesting.this, MainActivity.class));
        }


        Farmer activeFarmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();
        F_SESSION_ID = String.valueOf(activeFarmer.getFarmerId());
        F_LAND_AREA = activeFarmer.getArea();

        loadMinMaximumQs();

        isDataLoading = findViewById(R.id.loginLoadingPB);
        pQuantity = findViewById(R.id.pQuantity);
        sQuantity = findViewById(R.id.sQuantity);
        fQuantity = findViewById(R.id.fQuantity);
        farmerLandArea = findViewById(R.id.farmerLandArea);
        paymentStatus = findViewById(R.id.paymentStatus);
        itemPesticide = findViewById(R.id.itemPesticide);
        itemSeed = findViewById(R.id.itemSeed);
        itemFertilizer = findViewById(R.id.itemFertilizer);

        sMinimum = findViewById(R.id.sMinimum);
        fMinimum = findViewById(R.id.fMinimum);
        pMinimum = findViewById(R.id.pMinimum);

        seasonYear = findViewById(R.id.seasonYear);
        seasonTerm = findViewById(R.id.seasonTerm);
        saveButton = findViewById(R.id.saveButton);
        farmerLandArea.setText("(Your land area is: "+activeFarmer.getArea()+" meters)");


        // Heavy shit
        functionBox();

        saveButton.setOnClickListener(this);

        itemSeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Seed seed : seedArrayList) {
                    if(itemSeed.getSelectedItem().toString().trim().equals(seed.getItemName())) {
                        selectedSeed = seed.getItemId();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        itemFertilizer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Fertilizer fertilizer : fertilizerArrayList) {
                    if(itemFertilizer.getSelectedItem().toString().trim().equals(fertilizer.getItemName())) {
                        selectedFertilizer = fertilizer.getItemId();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        itemPesticide.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Pesticide pesticide : pesticideArrayList) {
                    if(itemPesticide.getSelectedItem().toString().trim().equals(pesticide.getItemName())) {
                        selectedPesticide = pesticide.getItemId();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    private void functionBox() {
        loadSeedData();
        loadFertilizerData();
        loadPesticideData();
    }

    private void loadSeedData() {
        showDamnProgressDialog(this, "Loading...","Loading all data...",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.SD_VIEW_ALL,
                response -> {
                    Log.d("strrrrr", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            seedArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("seeds");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Seed seed = new Seed();
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                seed.setItemId(dataObj.getInt("item_id"));
                                seed.setItemName(dataObj.getString("item_name"));
                                seed.setItemType(dataObj.getString("item_type"));
                                seed.setItemQuantity(dataObj.getInt("quantity"));
                                seed.setItemPrice(dataObj.getDouble("unit_price"));
                                seedArrayList.add(seed);
                            }

                            for (Seed seed : seedArrayList) {
                                seeds.add(seed.getItemName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(Requesting.this, simple_spinner_item, seeds);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            itemSeed.setAdapter(spinnerArrayAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void loadFertilizerData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.FR_VIEW_ALL,
                response -> {
                    Log.d("strrrrr", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            fertilizerArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("fertilizers");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Fertilizer fertilizer = new Fertilizer();
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                fertilizer.setItemId(dataObj.getInt("item_id"));
                                fertilizer.setItemName(dataObj.getString("item_name"));
                                fertilizer.setItemType(dataObj.getString("item_type"));
                                fertilizer.setItemQuantity(dataObj.getInt("quantity"));
                                fertilizer.setItemPrice(dataObj.getDouble("unit_price"));
                                fertilizerArrayList.add(fertilizer);
                            }

                            for (Fertilizer fertilizer : fertilizerArrayList) {
                                fertilizers.add(fertilizer.getItemName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(Requesting.this, simple_spinner_item, fertilizers);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            itemFertilizer.setAdapter(spinnerArrayAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void loadPesticideData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.PS_VIEW_ALL,
                response -> {
                    Log.d("strrrrr", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            pesticideArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("pesticides");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Pesticide pesticide = new Pesticide();
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                pesticide.setItemId(dataObj.getInt("item_id"));
                                pesticide.setItemName(dataObj.getString("item_name"));
                                pesticide.setItemType(dataObj.getString("item_type"));
                                pesticide.setItemQuantity(dataObj.getInt("quantity"));
                                pesticide.setItemPrice(dataObj.getDouble("unit_price"));
                                pesticideArrayList.add(pesticide);
                            }

                            for (Pesticide pesticide : pesticideArrayList) {
                                pesticides.add(pesticide.getItemName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(Requesting.this, simple_spinner_item, pesticides);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            itemPesticide.setAdapter(spinnerArrayAdapter);
                            exitDamnProgressDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private boolean inputsAreCorrect(String SQ, String FQ, String PQ, String sYear, String sTerm) {
        if (SQ.isEmpty()) {
            sQuantity.setError("Please enter seed quantity");
            sQuantity.requestFocus();
            return false;
        }

        if (FQ.isEmpty()) {
            fQuantity.setError("Please enter fertilizer quantity");
            fQuantity.requestFocus();
            return false;
        }

        if (PQ.isEmpty()) {
            pQuantity.setError("Please enter pesticide quantity");
            pQuantity.requestFocus();
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
    private void addData() {
        String PY;
        if(paymentStatus.getSelectedItem().toString().equals("PAID")) {
            PY = "Yes";
        } else if(paymentStatus.getSelectedItem().toString().equals("LOAN")){
            PY = "No";
        } else {
            PY = "Yes";
        }

        String SQ = sQuantity.getText().toString().trim();
        String FQ = fQuantity.getText().toString().trim();
        String PQ = pQuantity.getText().toString().trim();
        String sTerm = seasonTerm.getText().toString().trim();
        String sYear = seasonYear.getText().toString().trim();

        String SItem = String.valueOf(selectedSeed);
        String FItem = String.valueOf(selectedFertilizer);
        String PItem = String.valueOf(selectedPesticide);

        // Validating the inputs
        if(inputsAreCorrect(SQ, FQ, PQ, sYear, sTerm)) {
            isDataLoading.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FARMER_REQUEST,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(Requesting.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Requesting.this, Dashboard.class));
                            Toast.makeText(getApplicationContext(), "Wait for confirmation message to continue payment", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("is_paid", PY);
                    params.put("farmer_id", F_SESSION_ID);
                    params.put("seed_id", SItem);
                    params.put("seed_qty", SQ);
                    params.put("fert_id", FItem);
                    params.put("fert_qty", FQ);
                    params.put("pest_id", PItem);
                    params.put("pest_qty", PQ);
                    params.put("season_year",sYear);
                    params.put("season_term", sTerm);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }


    private void loadMinMaximumQs() {
        // Show minimum & max Q
        @SuppressLint("SetTextI18n")
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.FR_LIMITATION+F_LAND_AREA, response -> {
            try {
                JSONObject dataObject = new JSONObject(response);
                JSONArray dataArray = dataObject.getJSONArray("limitations");

                for(int i=0; i<dataArray.length(); i++) {
                    JSONObject dataObj = dataArray.getJSONObject(i);
                    sMinimum.setText("Maximum Quantity is: "+dataObj.getString("q_seeds")+" kgs)");
                    fMinimum.setText("Maximum Quantity is: "+dataObj.getString("q_fertilizer")+" kgs)");
                    pMinimum.setText("Maximum Quantity is: "+dataObj.getString("q_pesticide")+" doses)");
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.saveButton) {
            addData();
        }
    }
}