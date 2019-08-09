package ar.com.wolox.android.training.di

import ar.com.wolox.android.training.ui.example.ExampleActivity
import ar.com.wolox.android.training.ui.example.ExampleFragment
import ar.com.wolox.android.training.ui.training.details.DetailsActivity
import ar.com.wolox.android.training.ui.training.details.DetailsFragment
import ar.com.wolox.android.training.ui.training.login.LoginActivity
import ar.com.wolox.android.training.ui.training.login.LoginFragment
import ar.com.wolox.android.training.ui.training.main.MainActivity
import ar.com.wolox.android.training.ui.training.main.MainFragment
import ar.com.wolox.android.training.ui.training.main.tabs.news.NewsFragment
import ar.com.wolox.android.training.ui.training.main.tabs.profile.ProfileFragment
import ar.com.wolox.android.training.ui.training.singup.SingUpActivity
import ar.com.wolox.android.training.ui.training.singup.SingUpFragment
import ar.com.wolox.android.training.ui.youtube.YoutubeActivity
import ar.com.wolox.android.training.ui.youtube.YoutubeFragment
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
    internal abstract fun singUpActivity(): SingUpActivity

    @ContributesAndroidInjector
    internal abstract fun singUpFragment(): SingUpFragment

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun mainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun newsFragment(): NewsFragment

    @ContributesAndroidInjector
    internal abstract fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector
    internal abstract fun detailsActivity(): DetailsActivity

    @ContributesAndroidInjector
    internal abstract fun detailsFragment(): DetailsFragment

    @ContributesAndroidInjector
    internal abstract fun youtubeActivity(): YoutubeActivity

    @ContributesAndroidInjector
    internal abstract fun youtubeFragment(): YoutubeFragment
}
