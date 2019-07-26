package ar.com.wolox.android.training.di

import ar.com.wolox.android.training.ui.example.ExampleActivity
import ar.com.wolox.android.training.ui.example.ExampleFragment
import ar.com.wolox.android.training.ui.training.login.LoginActivity
import ar.com.wolox.android.training.ui.training.login.LoginFragment
import ar.com.wolox.android.training.ui.training.main.MainActivity
import ar.com.wolox.android.training.ui.training.main.MainFragment
import ar.com.wolox.android.training.ui.training.singup.SingUpActivity
import ar.com.wolox.android.training.ui.training.singup.SingUpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector
    internal abstract fun exampleActivity(): ExampleActivity

    @ContributesAndroidInjector
    internal abstract fun exampleFragment(): ExampleFragment

    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun loginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun mainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun singUpActivity(): SingUpActivity

    @ContributesAndroidInjector
    internal abstract fun singUpFragment(): SingUpFragment

}
