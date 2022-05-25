package com.donnekt.farmerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.farmerapp.farmer.Farmer;
import com.donnekt.farmerapp.farmer.Harvest;
import com.donnekt.farmerapp.farmer.HarvestAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HarvestViewAll extends AppCompatActivity {

    List<Harvest> harvestList;
    ListView listViewHarvests;
    TextView mainTitle;
    String F_SESSION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harvest_view_all);
        mainTitle = findViewById(R.id.mainTitle);
        mainTitle.setText("List of all harvests");
        listViewHarvests =  findViewById(R.id.listViewHarvests);
        harvestList = new ArrayList<>();

        Farmer activeFarmer = SharedPrefManager.getInstance(this).getLoggedInFarmer();
        F_SESSION_ID = String.valueOf(activeFarmer.getFarmerId());

        showAllHarvests();


    }

    private void showAllHarvests() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.HARVEST_VIEW_BY+F_SESSION_ID, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("harvests");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    Harvest harvest = new Harvest(
                            dataObject.getInt("harvest_id"),
                            dataObject.getInt("farmer_id"),
                            dataObject.getString("firstname"),
                            dataObject.getString("lastname"),
                            dataObject.getString("telephone"),
                            dataObject.getString("item_type"),
                            dataObject.getInt("quantity"),
                            dataObject.getDouble("harvest_price"),
                            dataObject.getInt("season_year"),
                            dataObject.getString("season_term"),
                            dataObject.getString("hv_date"),
                            dataObject.getString("status")
                    );

                    harvestList.add(harvest);
                    HarvestAdapter adapter = new HarvestAdapter(harvestList, getApplicationContext());
                    listViewHarvests.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network Problem", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}