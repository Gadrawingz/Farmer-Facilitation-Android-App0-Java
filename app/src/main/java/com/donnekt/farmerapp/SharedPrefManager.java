package com.donnekt.farmerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.donnekt.farmerapp.farmer.Farmer;

public class SharedPrefManager {
    private static SharedPrefManager sharedPrefManager;
    private static Context mct;

    private static final String SH_PREF_NAME="gPrefManager";
    private static final String KEY_ID="farmer_id";
    private static final String KEY_FIRSTNAME="firstname";
    private static final String KEY_LASTNAME="lastname";
    private static final String KEY_GENDER="gender";
    private static final String KEY_NID="national_id";
    private static final String KEY_PHONE="telephone";
    private static final String KEY_STATUS="status";
    private static final String KEY_AREA="land_area";
    private static final String KEY_PASSWORD ="password";


    private SharedPrefManager(Context context){
        mct=context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (sharedPrefManager==null){
            sharedPrefManager=new SharedPrefManager(context);
        }
        return sharedPrefManager;
    }

    public void farmerLogin(Farmer farmer) {
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();

        editor.putInt(KEY_ID, farmer.getFarmerId());
        editor.putString(KEY_FIRSTNAME, farmer.getFirstname());
        editor.putString(KEY_LASTNAME, farmer.getLastname());
        editor.putString(KEY_GENDER, farmer.getGender());
        editor.putString(KEY_NID, farmer.getNationalId());
        editor.putString(KEY_PHONE, farmer.getTelephone());
        editor.putString(KEY_STATUS, farmer.getStatus());
        editor.putString(KEY_AREA, farmer.getArea());
        editor.putString(KEY_PASSWORD, farmer.getPassword());
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME,Context.MODE_PRIVATE);
        return shPrefs.getString(KEY_PHONE, null) != null;
    }

    public void logout(){
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= shPrefs.edit();
        editor.clear();
        editor.apply();
        mct.startActivity(new Intent(mct, MainActivity.class));
    }

    public Farmer getLoggedInFarmer(){
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return new Farmer(
                shPrefs.getInt(KEY_ID, -1),
                shPrefs.getString(KEY_FIRSTNAME, null),
                shPrefs.getString(KEY_LASTNAME, null),
                shPrefs.getString(KEY_GENDER, null),
                shPrefs.getString(KEY_NID, null),
                shPrefs.getString(KEY_PHONE, null),
                shPrefs.getString(KEY_STATUS, null),
                shPrefs.getString(KEY_AREA, null),
                shPrefs.getString(KEY_PASSWORD, null)
        );
    }


}
