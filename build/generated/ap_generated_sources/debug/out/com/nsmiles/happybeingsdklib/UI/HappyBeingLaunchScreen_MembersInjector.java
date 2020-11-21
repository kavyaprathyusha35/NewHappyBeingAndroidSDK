package com.nsmiles.happybeingsdklib.UI;

import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
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
  private final Provider<DataManager> mDataManagerProvider;

  private final Provider<Service> serviceProvider;

  public HappyBeingLaunchScreen_MembersInjector(
      Provider<DataManager> mDataManagerProvider, Provider<Service> serviceProvider) {
    this.mDataManagerProvider = mDataManagerProvider;
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<HappyBeingLaunchScreen> create(
      Provider<DataManager> mDataManagerProvider, Provider<Service> serviceProvider) {
    return new HappyBeingLaunchScreen_MembersInjector(mDataManagerProvider, serviceProvider);
  }

  @Override
  public void injectMembers(HappyBeingLaunchScreen instance) {
    injectMDataManager(instance, mDataManagerProvider.get());
    injectService(instance, serviceProvider.get());
  }

  public static void injectMDataManager(HappyBeingLaunchScreen instance, DataManager mDataManager) {
    instance.mDataManager = mDataManager;
  }

  public static void injectService(HappyBeingLaunchScreen instance, Service service) {
    instance.service = service;
  }
}
