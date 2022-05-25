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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText editTextFName, editTextLName, editTextDOB, editTextNID, editTextPhone, editTextLandArea, editTextPassword;
    RadioGroup radioGroupGender;

    Spinner sProvince;
    EditText sDistrict, sSector, sCell, sVillage;
    ProgressBar loadingProgBar;
    TextView registerButton, closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loadingProgBar = findViewById(R.id.loadingProgress);
        editTextFName = findViewById(R.id.editTextFName);
        editTextLName = findViewById(R.id.editTextLName);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextNID = findViewById(R.id.editTextNID);
        editTextLandArea = findViewById(R.id.editTextLandArea);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGender);
        sProvince = findViewById(R.id.sProvince);
        sDistrict = findViewById(R.id.sDistrict);
        sSector = findViewById(R.id.sSector);
        sCell = findViewById(R.id.sCell);
        sVillage = findViewById(R.id.sVillage);
        registerButton = findViewById(R.id.registerButton);
        closeButton = findViewById(R.id.closeButton);
        //SharedPrefManager.getInstance(getApplicationContext()).logout();
        //startActivity(new Intent(SignupActivity.this, MainActivity.class));

        // Do some shit @gadrawingz
        registerButton.setOnClickListener(view -> registerData());
        closeButton.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, MainActivity.class)));
    }

    // my real quick function
    private void registerData() {
        final String fFName = editTextFName.getText().toString().trim();
        final String fLName = editTextLName.getText().toString().trim();
        final String SGender= ((RadioButton) findViewById(
                radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        final String fDoB = editTextDOB.getText().toString().trim();
        final String fNatId = editTextNID.getText().toString().trim();
        final String fPhone = editTextPhone.getText().toString().trim();
        final String fPass = editTextPassword.getText().toString().trim();
        final String fArea = editTextLandArea.getText().toString().trim();
        final String sProv = sProvince.getSelectedItem().toString();

        /*
        final String sDist = sDistrict.getSelectedItem().toString();
        final String sSect = sSector.getSelectedItem().toString();
        final String sCel0 = sCell.getSelectedItem().toString();
        final String sVil0 = sVillage.getSelectedItem().toString();*/

        final String sDist = sDistrict.getText().toString().trim();
        final String sSect = sSector.getText().toString().trim();
        final String sCel0 = sCell.getText().toString().trim();
        final String sVil0 = sVillage.getText().toString().trim();

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

        if (TextUtils.isEmpty(fDoB)) {
            editTextDOB.setError("Please enter your Date of Birth");
            editTextDOB.requestFocus();
            return;
        }

        if((fNatId).length() != 16) {
            editTextNID.setError("Valid ID should have 16 numbers!");
            editTextNID.requestFocus();
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
        transferDataByVolley(fFName, fLName, SGender, fDoB, fNatId, fPhone, fPass, fArea, sProv, sDist, sSect, sCel0, sVil0);
    }

    private void transferDataByVolley(String FNM, String LNM, String GDR, String DOB, String NID, String PHN, String PWD, String ARE, String PRO, String DST, String SCT, String CLL, String VLL) {
        loadingProgBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.FARMER_REGISTER, response -> {
            try {
                loadingProgBar.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(SignupActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                //emptyEditTextAfterInsertion();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(SignupActivity.this, "Network Problem!", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", FNM);
                params.put("lastname", LNM);
                params.put("gender", GDR);
                params.put("dob", DOB);
                params.put("national_id", NID);
                params.put("telephone", PHN);
                params.put("password", PWD);
                params.put("land_area", ARE);
                params.put("province", PRO);
                params.put("district", DST);
                params.put("sector", SCT);
                params.put("cell", CLL);
                params.put("village", VLL);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        emptyEditTextAfterInsertion();
    }

    private void emptyEditTextAfterInsertion() {
        editTextFName.getText().clear();
        editTextLName.getText().clear();
        editTextPhone.getText().clear();
        editTextDOB.getText().clear();
        editTextNID.getText().clear();
        editTextLandArea.getText().clear();
        editTextPassword.getText().clear();
    }
}