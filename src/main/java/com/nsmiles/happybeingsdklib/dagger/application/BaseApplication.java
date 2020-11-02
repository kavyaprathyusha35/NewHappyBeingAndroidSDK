package com.nsmiles.happybeingsdklib.dagger.application;

import android.content.Context;
import android.util.Log;

import com.nsmiles.happybeingsdklib.Utils.FontOverride;
import com.nsmiles.happybeingsdklib.network.NetworkModule;

import java.io.File;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

/**
 * Created by kavya on 4/13/2018.
 */

public class BaseApplication extends MultiDexApplication {

    private static final String AF_DEV_KEY = "Wmjzctc9TW8tuSy7xwjySh";
    private Deps mApplicationApiComponent;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("BaseApplication", "In base application");
/*

        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, this);
        AppsFlyerLib.getInstance().startTracking(this);

        File cacheFile = new File(getCacheDir(), "responses");
        mApplicationApiComponent = DaggerDeps.builder()
                .networkModule(new NetworkModule(cacheFile))
                .applicationModule(new ApplicationModule(this)).build();
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();

*/
        File cacheFile = new File(getCacheDir(), "responses");
        mApplicationApiComponent = DaggerDeps.builder()
                .networkModule(new NetworkModule(cacheFile))
                .applicationModule(new ApplicationModule(this)).build();

        FontOverride.setDefault(this, "DEFAULT","fonts/OpenSans-Regular.ttf");
        FontOverride.setDefault(this, "MONOSPACE", "fonts/roboto-medium.ttf");
        FontOverride.setDefault(this, "SERIF", "fonts/OpenSans-Light.ttf");
        FontOverride.setDefault(this, "SANS_SERIF", "fonts/OpenSans-Regular.ttf");

    }

    public Deps getmApplicationApiComponent() {
        return mApplicationApiComponent;
    }

}