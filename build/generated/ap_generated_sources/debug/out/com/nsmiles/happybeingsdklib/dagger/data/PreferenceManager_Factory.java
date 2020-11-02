package com.nsmiles.happybeingsdklib.dagger.data;

import android.content.Context;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PreferenceManager_Factory implements Factory<PreferenceManager> {
  private final Provider<Context> contextProvider;

  public PreferenceManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public PreferenceManager get() {
    return new PreferenceManager(contextProvider.get());
  }

  public static Factory<PreferenceManager> create(Provider<Context> contextProvider) {
    return new PreferenceManager_Factory(contextProvider);
  }
}
