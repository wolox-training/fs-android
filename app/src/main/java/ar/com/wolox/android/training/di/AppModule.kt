package ar.com.wolox.android.training.di

import ar.com.wolox.android.training.ui.example.ExampleActivity
import ar.com.wolox.android.training.ui.example.ExampleFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector
    internal abstract fun exampleActivity(): ExampleActivity

    @ContributesAndroidInjector
    internal abstract fun exampleFragment(): ExampleFragment
}
