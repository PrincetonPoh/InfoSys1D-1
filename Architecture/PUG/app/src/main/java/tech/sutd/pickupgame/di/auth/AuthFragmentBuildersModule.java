package tech.sutd.pickupgame.di.auth;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.ui.auth.GetStartedFragment;
import tech.sutd.pickupgame.ui.auth.login.LoginFragment;
import tech.sutd.pickupgame.ui.auth.register.RegisterFragment;

@Module
public abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract GetStartedFragment contributeGetStartedFragment();

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment contributeRegisterFragment();
}
