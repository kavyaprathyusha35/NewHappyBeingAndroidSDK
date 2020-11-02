package com.nsmiles.happybeingsdklib.wellbeingassessment.activity;

import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class WellBeingCategoryActivity_MembersInjector
    implements MembersInjector<WellBeingCategoryActivity> {
  private final Provider<DataManager> dataManagerProvider;

  private final Provider<Service> serviceProvider;

  public WellBeingCategoryActivity_MembersInjector(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    this.dataManagerProvider = dataManagerProvider;
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<WellBeingCategoryActivity> create(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    return new WellBeingCategoryActivity_MembersInjector(dataManagerProvider, serviceProvider);
  }

  @Override
  public void injectMembers(WellBeingCategoryActivity instance) {
    injectDataManager(instance, dataManagerProvider.get());
    injectService(instance, serviceProvider.get());
  }

  public static void injectDataManager(
      WellBeingCategoryActivity instance, DataManager dataManager) {
    instance.dataManager = dataManager;
  }

  public static void injectService(WellBeingCategoryActivity instance, Service service) {
    instance.service = service;
  }
}
