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
public final class WellBeingAssessmentActivity_MembersInjector
    implements MembersInjector<WellBeingAssessmentActivity> {
  private final Provider<DataManager> dataManagerProvider;

  private final Provider<Service> serviceProvider;

  public WellBeingAssessmentActivity_MembersInjector(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    this.dataManagerProvider = dataManagerProvider;
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<WellBeingAssessmentActivity> create(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    return new WellBeingAssessmentActivity_MembersInjector(dataManagerProvider, serviceProvider);
  }

  @Override
  public void injectMembers(WellBeingAssessmentActivity instance) {
    injectDataManager(instance, dataManagerProvider.get());
    injectService(instance, serviceProvider.get());
  }

  public static void injectDataManager(
      WellBeingAssessmentActivity instance, DataManager dataManager) {
    instance.dataManager = dataManager;
  }

  public static void injectService(WellBeingAssessmentActivity instance, Service service) {
    instance.service = service;
  }
}
