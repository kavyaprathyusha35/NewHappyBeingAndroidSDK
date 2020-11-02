package com.nsmiles.happybeingsdklib.dagger.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.nsmiles.happybeingsdklib.Utils.AppConstants;

import java.util.Set;

import javax.inject.Inject;

/**
 * Created by Sukumar on 4/28/2018.
 */

public class PreferenceManager {


    private SharedPreferences pref;

    /*
    *Injecting Shared Preferences Using Dagger
    *Shared Preference data passing from Application Module
    **/
    @Inject
    public PreferenceManager(Context context) {
        this.pref  = context.getSharedPreferences(AppConstants.SHARED_HAPPY_BEING, Context.MODE_PRIVATE);;
    }


    public void add(String key, String value) {
        pref.edit().putString(key, value).apply();
    }

    public void add(String key, int value) {
        pref.edit().putInt(key, value).apply();
    }

    public void add(String key, boolean value) {
        pref.edit().putBoolean(key, value).apply();
    }

    public void add(String key, Set<String> value){
        pref.edit().putStringSet(key,value).apply();
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


    public void clear() {
        pref.edit().clear().apply();
    }


}
