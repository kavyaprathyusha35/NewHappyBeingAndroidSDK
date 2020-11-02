package com.nsmiles.happybeingsdklib.UI.gratitude;

import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ExpressGratitudeOthers_MembersInjector
    implements MembersInjector<ExpressGratitudeOthers> {
  private final Provider<DataManager> dataManagerProvider;

  private final Provider<Service> serviceProvider;

  public ExpressGratitudeOthers_MembersInjector(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    this.dataManagerProvider = dataManagerProvider;
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<ExpressGratitudeOthers> create(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    return new ExpressGratitudeOthers_MembersInjector(dataManagerProvider, serviceProvider);
  }

  @Override
  public void injectMembers(ExpressGratitudeOthers instance) {
    injectDataManager(instance, dataManagerProvider.get());
    injectService(instance, serviceProvider.get());
  }

  public static void injectDataManager(ExpressGratitudeOthers instance, DataManager dataManager) {
    instance.dataManager = dataManager;
  }

  public static void injectService(ExpressGratitudeOthers instance, Service service) {
    instance.service = service;
  }
}
