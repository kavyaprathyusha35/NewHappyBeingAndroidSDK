package com.nsmiles.happybeingsdklib.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Kavya on 9/21/2020.
 */

public class SdkPreferenceManager {
    private SharedPreferences pref;

    public SdkPreferenceManager(Context context) {
        pref = context.getSharedPreferences(AppConstants.SHARED_HAPPY_BEING, Context.MODE_PRIVATE);
    }
















    public void save(String key, String value) {
        pref.edit().putString(key, value).apply();
    }
    public void save(String key, int value) {
        pref.edit().putInt(key, value).apply();
    }
    public void save(String key, boolean value) {
        pref.edit().putBoolean(key, value).apply();
    }
    public String get(String key, String defaultValue) {

        return pref.getString(key, defaultValue);
    }

    public Integer get(String key, int defaultValue) {
        return pref.getInt(key, defaultValue);
    }

    public boolean get(String key, boolean defaultValue) {
        return pref.getBoolean(key, defaultValue);
    }

    public Set<String> get(String key){
        return pref.getStringSet(key,null);
    }

}
