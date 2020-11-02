package com.nsmiles.happybeingsdklib.dagger.application;

import android.content.SharedPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ApplicationModule_ProvideSharedPreferencesFactory
    implements Factory<SharedPreferences> {
  private final ApplicationModule module;

  public ApplicationModule_ProvideSharedPreferencesFactory(ApplicationModule module) {
    this.module = module;
  }

  @Override
  public SharedPreferences get() {
    return Preconditions.checkNotNull(
        module.provideSharedPreferences(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<SharedPreferences> create(ApplicationModule module) {
    return new ApplicationModule_ProvideSharedPreferencesFactory(module);
  }

  public static SharedPreferences proxyProvideSharedPreferences(ApplicationModule instance) {
    return instance.provideSharedPreferences();
  }
}
