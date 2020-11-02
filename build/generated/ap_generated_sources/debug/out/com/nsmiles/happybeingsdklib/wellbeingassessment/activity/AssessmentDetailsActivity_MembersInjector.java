package com.nsmiles.happybeingsdklib.wellbeingassessment.activity;

import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AssessmentDetailsActivity_MembersInjector
    implements MembersInjector<AssessmentDetailsActivity> {
  private final Provider<DataManager> dataManagerProvider;

  public AssessmentDetailsActivity_MembersInjector(Provider<DataManager> dataManagerProvider) {
    this.dataManagerProvider = dataManagerProvider;
  }

  public static MembersInjector<AssessmentDetailsActivity> create(
      Provider<DataManager> dataManagerProvider) {
    return new AssessmentDetailsActivity_MembersInjector(dataManagerProvider);
  }

  @Override
  public void injectMembers(AssessmentDetailsActivity instance) {
    injectDataManager(instance, dataManagerProvider.get());
  }

  public static void injectDataManager(
      AssessmentDetailsActivity instance, DataManager dataManager) {
    instance.dataManager = dataManager;
  }
}
