package com.donnekt.farmerapp.farmer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.farmerapp.*;
import com.donnekt.farmerapp.farmer.Farmer;
import com.donnekt.farmerapp.farmer.Harvest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.view.View.GONE;

public class HarvestAdapter extends ArrayAdapter<Harvest> {

    private final List<Harvest> harvestList;
    private final Context mCtx;

    public HarvestAdapter(List<Harvest> harvestList, Context mCtx) {
        super(mCtx, R.layout.list_layout_harvest, harvestList);
        this.mCtx = mCtx;
        this.harvestList = harvestList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_harvest, null, true);

        final TextView tvType = LVItem.findViewById(R.id.tvHarvestType);
        final TextView tvQuantity = LVItem.findViewById(R.id.tvQuantity);
        final TextView tvPrice = LVItem.findViewById(R.id.tvPrice);
        final TextView tvSeasonYearTerm = LVItem.findViewById(R.id.tvSeasonYearTerm);
        final TextView tvHarvestDate = LVItem.findViewById(R.id.tvHarvestDate);
        final TextView tvEditButton = LVItem.findViewById(R.id.buttonEdit);
        final TextView tvChecked = LVItem.findViewById(R.id.buttonChecked);
        final TextView harvestStatus = LVItem.findViewById(R.id.harvestStatus);

        // Getting record of the specified position
        Harvest harvest = harvestList.get(position);

        tvType.setText("Harvest: "+harvest.getItemType());
        tvPrice.setText(String.format("Total.Price: %s", (harvest.getQuantity() * harvest.getHarvestPrice()))+" rwf");
        tvQuantity.setText(String.format("Quantity: %d", harvest.getQuantity())+String.format("/ %s", harvest.getHarvestPrice())+" rwf.");
        tvHarvestDate.setText(harvest.getHarvestDate());
        tvSeasonYearTerm.setText("S.Year: "+harvest.getSeasonYear()+", Term: "+harvest.getSeasonTerm());


        if(harvest.getStatus().equals("unchecked")) {
            harvestStatus.setText("Pending");
            tvChecked.setVisibility(GONE);
        } else {
            tvChecked.setVisibility(View.VISIBLE);
            tvEditButton.setVisibility(GONE);
            harvestStatus.setVisibility(GONE);
        }

        String harvestID= String.valueOf(harvest.getHarvestId());
        String TYPE= String.valueOf(harvest.getItemType());
        String QTY = String.valueOf(harvest.getQuantity());
        String PRICE= String.valueOf(harvest.getHarvestPrice());
        String TERM= String.valueOf(harvest.getSeasonTerm());
        String YEAR= String.valueOf(harvest.getSeasonYear());

        // UPDATE SHIT
        tvEditButton.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), HarvestUpdate.class);
            g.putExtra("harvest_id_key", harvestID);
            g.putExtra("type_key", TYPE);
            g.putExtra("quantity_key", QTY);
            g.putExtra("price_key", PRICE);
            g.putExtra("term_key", TERM);
            g.putExtra("year_key", YEAR);
            mCtx.startActivity(g);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // For killing activity
        });

        // Return the list item
        return LVItem;
    }
}
