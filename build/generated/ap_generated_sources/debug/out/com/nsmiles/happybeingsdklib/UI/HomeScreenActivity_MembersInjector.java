package com.nsmiles.happybeingsdklib.UI;

import com.nsmiles.happybeingsdklib.network.Service;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HomeScreenActivity_MembersInjector
    implements MembersInjector<HomeScreenActivity> {
  private final Provider<Service> serviceProvider;

  public HomeScreenActivity_MembersInjector(Provider<Service> serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<HomeScreenActivity> create(Provider<Service> serviceProvider) {
    return new HomeScreenActivity_MembersInjector(serviceProvider);
  }

  @Override
  public void injectMembers(HomeScreenActivity instance) {
    injectService(instance, serviceProvider.get());
  }

  public static void injectService(HomeScreenActivity instance, Service service) {
    instance.service = service;
  }
}
