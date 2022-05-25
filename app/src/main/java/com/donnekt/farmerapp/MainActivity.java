package com.donnekt.farmerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.farmerapp.farmer.Farmer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public TextView createAccount, buttonLogin;
    EditText editTextPhone, editTextPassword;
    ProgressBar loginLoadingPB;
    SharedPrefManager sharedPrefManager;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(MainActivity.this, Dashboard.class));
        }

        // INITIALIZATION
        createAccount = findViewById(R.id.createAccount);
        buttonLogin = findViewById(R.id.buttonLogin);
        loginLoadingPB = findViewById(R.id.loginLoadingPB);
        editTextPhone = findViewById(R.id.textNumber);
        editTextPassword = findViewById(R.id.textPassword);

        createAccount.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,SignupActivity.class)));
        buttonLogin.setOnClickListener(v -> farmerLogin());
    }

    private void farmerLogin() {
        // Getting the values
        final String telephone = editTextPhone.getText().toString();
        final String password = editTextPassword.getText().toString();

        // Validating inputs
        if (TextUtils.isEmpty(telephone)) {
            editTextPhone.setError("Please enter your phone");
            editTextPhone.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(telephone).matches()) {
            editTextPhone.setError("Please enter valid number!");
            editTextPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password!");
            editTextPassword.requestFocus();
            return;
        }

        // If validation goes on like a bomb
        loginLoadingPB.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FARMER_LOGIN, response -> {
            requestQueue.getCache().clear();
            loginLoadingPB.setVisibility(View.GONE);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.optString("status").equals("ok")) {

                    JSONArray myJson = jsonObject.getJSONArray("details");
                    for (int k=0; k < myJson.length(); k++) {

                        JSONObject myObject = myJson.getJSONObject(k);
                        Farmer farmer = new Farmer(
                                myObject.getInt("farmer_id"),
                                myObject.getString("firstname"),
                                myObject.getString("lastname"),
                                myObject.getString("gender"),
                                myObject.getString("national_id"),
                                myObject.getString("telephone"),
                                myObject.getString("status"),
                                myObject.getString("land_area"),
                                myObject.getString("password")
                        );

                        // Store Preferences
                        SharedPrefManager.getInstance(getApplicationContext()).farmerLogin(farmer);
                        finish();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    }
                } else {
                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(MainActivity.this, "Network Problem Occurred!", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("telephone", telephone);
                params.put("password", password);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}