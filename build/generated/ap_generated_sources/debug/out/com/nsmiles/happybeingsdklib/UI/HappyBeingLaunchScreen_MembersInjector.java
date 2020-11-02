package com.nsmiles.happybeingsdklib.UI;

import com.nsmiles.happybeingsdklib.network.Service;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HappyBeingLaunchScreen_MembersInjector
    implements MembersInjector<HappyBeingLaunchScreen> {
  private final Provider<Service> serviceProvider;

  public HappyBeingLaunchScreen_MembersInjector(Provider<Service> serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<HappyBeingLaunchScreen> create(Provider<Service> serviceProvider) {
    return new HappyBeingLaunchScreen_MembersInjector(serviceProvider);
  }

  @Override
  public void injectMembers(HappyBeingLaunchScreen instance) {
    injectService(instance, serviceProvider.get());
  }

  public static void injectService(HappyBeingLaunchScreen instance, Service service) {
    instance.service = service;
  }
}
