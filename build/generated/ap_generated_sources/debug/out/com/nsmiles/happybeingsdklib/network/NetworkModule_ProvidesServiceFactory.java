package com.nsmiles.happybeingsdklib.network;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkModule_ProvidesServiceFactory implements Factory<Service> {
  private final NetworkModule module;

  private final Provider<NetworkService> networkServiceProvider;

  public NetworkModule_ProvidesServiceFactory(
      NetworkModule module, Provider<NetworkService> networkServiceProvider) {
    this.module = module;
    this.networkServiceProvider = networkServiceProvider;
  }

  @Override
  public Service get() {
    return Preconditions.checkNotNull(
        module.providesService(networkServiceProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Service> create(
      NetworkModule module, Provider<NetworkService> networkServiceProvider) {
    return new NetworkModule_ProvidesServiceFactory(module, networkServiceProvider);
  }
}
