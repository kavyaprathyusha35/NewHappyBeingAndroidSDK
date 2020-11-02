package com.nsmiles.happybeingsdklib.dagger.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nsmiles.happybeingsdklib.Utils.AppConstants;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sukumar on 4/13/2018.
 */

@Module
public class ApplicationModule {

    private final Application application;
    private MySql db;

    public ApplicationModule(Application application) {
        this.application = application;
        db = new MySql(application,"mydb", MySql.version);

    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }


    @Provides
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences(AppConstants.SHARED_HAPPY_BEING, Context.MODE_PRIVATE);
    }

    @Provides
    MySql provideMyDatabaseContext() {
    return db;
    }

}
