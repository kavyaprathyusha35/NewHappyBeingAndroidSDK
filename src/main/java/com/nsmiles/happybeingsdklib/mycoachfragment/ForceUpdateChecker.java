package com.nsmiles.happybeingsdklib.mycoachfragment;

import android.content.Context;
import android.util.Log;

import com.nsmiles.happybeingsdklib.BuildConfig;

import androidx.annotation.NonNull;

/**
 * Created by nSmiles on 10/12/2017.
 */

public class ForceUpdateChecker {


    private static final String TAG = ForceUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "force_update_required";
    public static final String KEY_CURRENT_VERSION = "force_update_current_version";
    public static final String KEY_UPDATE_URL = "force_update_store_url";

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    public interface OnUpdateNeededListener {
        void onUpdateNeeded(String updateUrl);
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public ForceUpdateChecker(@NonNull Context context,
                              OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

/*
    public void check() {
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);
            int appVersion = getAppVersion(context);
            String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);

            if (Integer.parseInt(currentVersion) > appVersion && onUpdateNeededListener != null) {
                onUpdateNeededListener.onUpdateNeeded(updateUrl);
            }
        }
    }
*/

    private int getAppVersion(Context context) {
        int result = 0;

        try {
            result = BuildConfig.VERSION_CODE;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }

        public ForceUpdateChecker build() {
            return new ForceUpdateChecker(context, onUpdateNeededListener);
        }

        public ForceUpdateChecker check() {
            ForceUpdateChecker forceUpdateChecker = build();
            //forceUpdateChecker.check();

            return forceUpdateChecker;
        }
    }
}
