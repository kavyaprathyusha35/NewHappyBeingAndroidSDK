package com.nsmiles.happybeingsdklib.dagger.data;

import android.content.Context;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HappyUtils_Factory implements Factory<HappyUtils> {
  private final Provider<Context> contextProvider;

  public HappyUtils_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HappyUtils get() {
    return new HappyUtils(contextProvider.get());
  }

  public static Factory<HappyUtils> create(Provider<Context> contextProvider) {
    return new HappyUtils_Factory(contextProvider);
  }
}
