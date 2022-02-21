package com.example.my_movie_db.di

import android.app.Application
import android.content.Context
import com.example.my_movie_db.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MyMovieDBBuilderModule::class,
        MyMovieDBModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class
    ]
)
interface ApplicationComponent : AndroidInjector<MyApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}