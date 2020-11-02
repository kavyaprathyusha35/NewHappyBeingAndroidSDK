package com.nsmiles.happybeingsdklib.dagger.application;

import android.content.Context;
import com.nsmiles.happybeingsdklib.Reports.DetailReport.DetailReportActivity;
import com.nsmiles.happybeingsdklib.Reports.DetailReport.DetailReportActivity_MembersInjector;
import com.nsmiles.happybeingsdklib.UI.HappyBeingLaunchScreen;
import com.nsmiles.happybeingsdklib.UI.HappyBeingLaunchScreen_MembersInjector;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity_MembersInjector;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeOthers;
import com.nsmiles.happybeingsdklib.UI.gratitude.ExpressGratitudeOthers_MembersInjector;
import com.nsmiles.happybeingsdklib.broadcast.NetworkChangeReceiver;
import com.nsmiles.happybeingsdklib.broadcast.NetworkChangeReceiver_MembersInjector;
import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowRelaxBigNotification;
import com.nsmiles.happybeingsdklib.broadcastnotifications.ShowRelaxBigNotification_MembersInjector;
import com.nsmiles.happybeingsdklib.dagger.data.DataManager;
import com.nsmiles.happybeingsdklib.dagger.data.HappyUtils;
import com.nsmiles.happybeingsdklib.dagger.data.PreferenceManager;
import com.nsmiles.happybeingsdklib.network.NetworkModule;
import com.nsmiles.happybeingsdklib.network.NetworkModule_ProvideCallFactory;
import com.nsmiles.happybeingsdklib.network.NetworkModule_ProvidesNetworkServiceFactory;
import com.nsmiles.happybeingsdklib.network.NetworkModule_ProvidesServiceFactory;
import com.nsmiles.happybeingsdklib.network.NetworkService;
import com.nsmiles.happybeingsdklib.network.Service;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.WellBeingAssessmentActivity;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.WellBeingAssessmentActivity_MembersInjector;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.WellBeingCategoryActivity;
import com.nsmiles.happybeingsdklib.wellbeingassessment.activity.WellBeingCategoryActivity_MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerDeps implements Deps {
  private Provider<Context> provideContextProvider;

  private ApplicationModule applicationModule;

  private Provider<Retrofit> provideCallProvider;

  private Provider<NetworkService> providesNetworkServiceProvider;

  private Provider<Service> providesServiceProvider;

  private DaggerDeps(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  private PreferenceManager getPreferenceManager() {
    return new PreferenceManager(provideContextProvider.get());
  }

  private HappyUtils getHappyUtils() {
    return new HappyUtils(provideContextProvider.get());
  }

  private DataManager getDataManager() {
    return new DataManager(
        provideContextProvider.get(),
        Preconditions.checkNotNull(
            applicationModule.provideMyDatabaseContext(),
            "Cannot return null from a non-@Nullable @Provides method"),
        getPreferenceManager(),
        getHappyUtils());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideContextProvider =
        DoubleCheck.provider(
            ApplicationModule_ProvideContextFactory.create(builder.applicationModule));
    this.applicationModule = builder.applicationModule;
    this.provideCallProvider =
        DoubleCheck.provider(NetworkModule_ProvideCallFactory.create(builder.networkModule));
    this.providesNetworkServiceProvider =
        DoubleCheck.provider(
            NetworkModule_ProvidesNetworkServiceFactory.create(
                builder.networkModule, provideCallProvider));
    this.providesServiceProvider =
        DoubleCheck.provider(
            NetworkModule_ProvidesServiceFactory.create(
                builder.networkModule, providesNetworkServiceProvider));
  }

  @Override
  public void inject(WellBeingAssessmentActivity nSmilesAssessmentActivity) {
    injectWellBeingAssessmentActivity(nSmilesAssessmentActivity);
  }

  @Override
  public void inject(HappyBeingLaunchScreen happyBeingLaunchScreen) {
    injectHappyBeingLaunchScreen(happyBeingLaunchScreen);
  }

  @Override
  public void inject(WellBeingCategoryActivity nSmilesAssessmentCategoryActivity) {
    injectWellBeingCategoryActivity(nSmilesAssessmentCategoryActivity);
  }

  @Override
  public void inject(DetailReportActivity detailReportActivity) {
    injectDetailReportActivity(detailReportActivity);
  }

  @Override
  public void inject(NetworkChangeReceiver networkChangeReceiver) {
    injectNetworkChangeReceiver(networkChangeReceiver);
  }

  @Override
  public void inject(ShowRelaxBigNotification showRelaxBigNotification) {
    injectShowRelaxBigNotification(showRelaxBigNotification);
  }

  @Override
  public void inject(ExpressGratitudeOthers showRelaxBigNotification) {
    injectExpressGratitudeOthers(showRelaxBigNotification);
  }

  @Override
  public void inject(HomeScreenActivity showRelaxBigNotification) {
    injectHomeScreenActivity(showRelaxBigNotification);
  }

  private WellBeingAssessmentActivity injectWellBeingAssessmentActivity(
      WellBeingAssessmentActivity instance) {
    WellBeingAssessmentActivity_MembersInjector.injectDataManager(instance, getDataManager());
    WellBeingAssessmentActivity_MembersInjector.injectService(
        instance, providesServiceProvider.get());
    return instance;
  }

  private HappyBeingLaunchScreen injectHappyBeingLaunchScreen(HappyBeingLaunchScreen instance) {
    HappyBeingLaunchScreen_MembersInjector.injectService(instance, providesServiceProvider.get());
    return instance;
  }

  private WellBeingCategoryActivity injectWellBeingCategoryActivity(
      WellBeingCategoryActivity instance) {
    WellBeingCategoryActivity_MembersInjector.injectDataManager(instance, getDataManager());
    WellBeingCategoryActivity_MembersInjector.injectService(
        instance, providesServiceProvider.get());
    return instance;
  }

  private DetailReportActivity injectDetailReportActivity(DetailReportActivity instance) {
    DetailReportActivity_MembersInjector.injectDataManager(instance, getDataManager());
    DetailReportActivity_MembersInjector.injectService(instance, providesServiceProvider.get());
    return instance;
  }

  private NetworkChangeReceiver injectNetworkChangeReceiver(NetworkChangeReceiver instance) {
    NetworkChangeReceiver_MembersInjector.injectService(instance, providesServiceProvider.get());
    return instance;
  }

  private ShowRelaxBigNotification injectShowRelaxBigNotification(
      ShowRelaxBigNotification instance) {
    ShowRelaxBigNotification_MembersInjector.injectDataManager(instance, getDataManager());
    return instance;
  }

  private ExpressGratitudeOthers injectExpressGratitudeOthers(ExpressGratitudeOthers instance) {
    ExpressGratitudeOthers_MembersInjector.injectDataManager(instance, getDataManager());
    ExpressGratitudeOthers_MembersInjector.injectService(instance, providesServiceProvider.get());
    return instance;
  }

  private HomeScreenActivity injectHomeScreenActivity(HomeScreenActivity instance) {
    HomeScreenActivity_MembersInjector.injectService(instance, providesServiceProvider.get());
    return instance;
  }

  public static final class Builder {
    private ApplicationModule applicationModule;

    private NetworkModule networkModule;

    private Builder() {}

    public Deps build() {
      if (applicationModule == null) {
        throw new IllegalStateException(
            ApplicationModule.class.getCanonicalName() + " must be set");
      }
      if (networkModule == null) {
        throw new IllegalStateException(NetworkModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerDeps(this);
    }

    public Builder applicationModule(ApplicationModule applicationModule) {
      this.applicationModule = Preconditions.checkNotNull(applicationModule);
      return this;
    }

    public Builder networkModule(NetworkModule networkModule) {
      this.networkModule = Preconditions.checkNotNull(networkModule);
      return this;
    }
  }
}
