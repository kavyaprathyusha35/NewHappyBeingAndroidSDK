package com.nsmiles.happybeingsdklib.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvidesNetworkServiceFactory implements Factory<NetworkService> {
  private final NetworkModule module;

  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvidesNetworkServiceFactory(
      NetworkModule module, Provider<Retrofit> retrofitProvider) {
    this.module = module;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public NetworkService get() {
    return Preconditions.checkNotNull(
        module.providesNetworkService(retrofitProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<NetworkService> create(
      NetworkModule module, Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvidesNetworkServiceFactory(module, retrofitProvider);
  }
}
