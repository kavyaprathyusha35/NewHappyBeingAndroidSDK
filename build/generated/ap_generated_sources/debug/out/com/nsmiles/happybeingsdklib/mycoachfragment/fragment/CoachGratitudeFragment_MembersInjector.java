package com.nsmiles.happybeingsdklib.mycoachfragment.fragment;

import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CoachGratitudeFragment_MembersInjector
    implements MembersInjector<CoachGratitudeFragment> {
  private final Provider<Service> serviceProvider;

  private final Provider<DataManager> dataManagerProvider;

  public CoachGratitudeFragment_MembersInjector(
      Provider<Service> serviceProvider, Provider<DataManager> dataManagerProvider) {
    this.serviceProvider = serviceProvider;
    this.dataManagerProvider = dataManagerProvider;
  }

  public static MembersInjector<CoachGratitudeFragment> create(
      Provider<Service> serviceProvider, Provider<DataManager> dataManagerProvider) {
    return new CoachGratitudeFragment_MembersInjector(serviceProvider, dataManagerProvider);
  }

  @Override
  public void injectMembers(CoachGratitudeFragment instance) {
    injectService(instance, serviceProvider.get());
    injectDataManager(instance, dataManagerProvider.get());
  }

  public static void injectService(CoachGratitudeFragment instance, Service service) {
    instance.service = service;
  }

  public static void injectDataManager(CoachGratitudeFragment instance, DataManager dataManager) {
    instance.dataManager = dataManager;
  }
}
