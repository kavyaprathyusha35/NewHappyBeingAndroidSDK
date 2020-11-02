package com.nsmiles.happybeingsdklib.broadcastnotifications;

import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ShowRelaxBigNotification_MembersInjector
    implements MembersInjector<ShowRelaxBigNotification> {
  private final Provider<DataManager> dataManagerProvider;

  public ShowRelaxBigNotification_MembersInjector(Provider<DataManager> dataManagerProvider) {
    this.dataManagerProvider = dataManagerProvider;
  }

  public static MembersInjector<ShowRelaxBigNotification> create(
      Provider<DataManager> dataManagerProvider) {
    return new ShowRelaxBigNotification_MembersInjector(dataManagerProvider);
  }

  @Override
  public void injectMembers(ShowRelaxBigNotification instance) {
    injectDataManager(instance, dataManagerProvider.get());
  }

  public static void injectDataManager(ShowRelaxBigNotification instance, DataManager dataManager) {
    instance.dataManager = dataManager;
  }
}
