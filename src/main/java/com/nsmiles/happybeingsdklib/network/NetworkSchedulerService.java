package com.nsmiles.happybeingsdklib.network;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import com.nsmiles.happybeingsdklib.broadcast.NetworkChangeReceiver;

import androidx.annotation.RequiresApi;

/**
 * Created by Sukumar on 11/9/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkSchedulerService extends JobService implements
        NetworkChangeReceiver.ConnectivityReceiverListener {
    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private NetworkChangeReceiver mConnectivityReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        mConnectivityReceiver = new NetworkChangeReceiver(this);
    }


    /**
     * When the app's NetworkConnectionActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob" + mConnectivityReceiver);
        registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        try {
            unregisterReceiver(mConnectivityReceiver);
        }
        catch (IllegalStateException ie) {
            ie.printStackTrace();
        }
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        String message = isConnected ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
}
