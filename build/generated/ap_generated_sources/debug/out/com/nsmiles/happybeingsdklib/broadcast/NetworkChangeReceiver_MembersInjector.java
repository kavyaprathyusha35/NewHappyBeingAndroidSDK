package com.nsmiles.happybeingsdklib.broadcast;

import com.nsmiles.happybeingsdklib.network.Service;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NetworkChangeReceiver_MembersInjector
    implements MembersInjector<NetworkChangeReceiver> {
  private final Provider<Service> serviceProvider;

  public NetworkChangeReceiver_MembersInjector(Provider<Service> serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<NetworkChangeReceiver> create(Provider<Service> serviceProvider) {
    return new NetworkChangeReceiver_MembersInjector(serviceProvider);
  }

  @Override
  public void injectMembers(NetworkChangeReceiver instance) {
    injectService(instance, serviceProvider.get());
  }

  public static void injectService(NetworkChangeReceiver instance, Service service) {
    instance.service = service;
  }
}
