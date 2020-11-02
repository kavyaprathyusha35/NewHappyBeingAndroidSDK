package com.nsmiles.happybeingsdklib.Reports.DetailReport;

import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.network.Service;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DetailReportActivity_MembersInjector
    implements MembersInjector<DetailReportActivity> {
  private final Provider<DataManager> dataManagerProvider;

  private final Provider<Service> serviceProvider;

  public DetailReportActivity_MembersInjector(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    this.dataManagerProvider = dataManagerProvider;
    this.serviceProvider = serviceProvider;
  }

  public static MembersInjector<DetailReportActivity> create(
      Provider<DataManager> dataManagerProvider, Provider<Service> serviceProvider) {
    return new DetailReportActivity_MembersInjector(dataManagerProvider, serviceProvider);
  }

  @Override
  public void injectMembers(DetailReportActivity instance) {
    injectDataManager(instance, dataManagerProvider.get());
    injectService(instance, serviceProvider.get());
  }

  public static void injectDataManager(DetailReportActivity instance, DataManager dataManager) {
    instance.dataManager = dataManager;
  }

  public static void injectService(DetailReportActivity instance, Service service) {
    instance.service = service;
  }
}
