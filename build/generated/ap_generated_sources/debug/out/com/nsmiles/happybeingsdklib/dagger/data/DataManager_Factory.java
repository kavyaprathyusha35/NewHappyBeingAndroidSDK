package com.nsmiles.happybeingsdklib.dagger.data;

import android.content.Context;
import com.nsmiles.happybeingsdklib.Utils.MySql;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DataManager_Factory implements Factory<DataManager> {
  private final Provider<Context> contextProvider;

  private final Provider<MySql> dbHelperProvider;

  private final Provider<PreferenceManager> preferenceManagerProvider;

  private final Provider<HappyUtils> happyUtilsProvider;

  public DataManager_Factory(
      Provider<Context> contextProvider,
      Provider<MySql> dbHelperProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<HappyUtils> happyUtilsProvider) {
    this.contextProvider = contextProvider;
    this.dbHelperProvider = dbHelperProvider;
    this.preferenceManagerProvider = preferenceManagerProvider;
    this.happyUtilsProvider = happyUtilsProvider;
  }

  @Override
  public DataManager get() {
    return new DataManager(
        contextProvider.get(),
        dbHelperProvider.get(),
        preferenceManagerProvider.get(),
        happyUtilsProvider.get());
  }

  public static Factory<DataManager> create(
      Provider<Context> contextProvider,
      Provider<MySql> dbHelperProvider,
      Provider<PreferenceManager> preferenceManagerProvider,
      Provider<HappyUtils> happyUtilsProvider) {
    return new DataManager_Factory(
        contextProvider, dbHelperProvider, preferenceManagerProvider, happyUtilsProvider);
  }
}
