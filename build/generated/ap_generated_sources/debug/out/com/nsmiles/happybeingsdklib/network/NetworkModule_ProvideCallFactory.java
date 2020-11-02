package com.nsmiles.happybeingsdklib.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvideCallFactory implements Factory<Retrofit> {
  private final NetworkModule module;

  public NetworkModule_ProvideCallFactory(NetworkModule module) {
    this.module = module;
  }

  @Override
  public Retrofit get() {
    return Preconditions.checkNotNull(
        module.provideCall(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Retrofit> create(NetworkModule module) {
    return new NetworkModule_ProvideCallFactory(module);
  }

  public static Retrofit proxyProvideCall(NetworkModule instance) {
    return instance.provideCall();
  }
}
