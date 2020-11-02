package com.nsmiles.happybeingsdklib.dagger.application;

import com.nsmiles.happybeingsdklib.Utils.MySql;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ApplicationModule_ProvideMyDatabaseContextFactory implements Factory<MySql> {
  private final ApplicationModule module;

  public ApplicationModule_ProvideMyDatabaseContextFactory(ApplicationModule module) {
    this.module = module;
  }

  @Override
  public MySql get() {
    return Preconditions.checkNotNull(
        module.provideMyDatabaseContext(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<MySql> create(ApplicationModule module) {
    return new ApplicationModule_ProvideMyDatabaseContextFactory(module);
  }

  public static MySql proxyProvideMyDatabaseContext(ApplicationModule instance) {
    return instance.provideMyDatabaseContext();
  }
}
