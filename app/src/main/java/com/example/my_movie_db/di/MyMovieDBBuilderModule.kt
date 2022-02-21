package com.example.my_movie_db.di

import com.example.my_movie_db.di.detail.DetailModule
import com.example.my_movie_db.di.detail.DetailScope
import com.example.my_movie_db.di.favorite.FavoriteModule
import com.example.my_movie_db.di.favorite.FavoriteScope
import com.example.my_movie_db.di.home.HomeModule
import com.example.my_movie_db.di.home.HomeScope
import com.example.my_movie_db.di.location.LocationModule
import com.example.my_movie_db.di.location.LocationScope
import com.example.my_movie_db.di.popular.PopularModule
import com.example.my_movie_db.di.popular.PopularScope
import com.example.my_movie_db.fragment.detail.DetailFragment
import com.example.my_movie_db.fragment.favorite.FavoriteFragment
import com.example.my_movie_db.fragment.home.HomeFragment
import com.example.my_movie_db.fragment.location.LocationFragment
import com.example.my_movie_db.fragment.popular.PopularFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MyMovieDBBuilderModule {
    @HomeScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun homeFragment(): HomeFragment

    @PopularScope
    @ContributesAndroidInjector(modules = [PopularModule::class])
    internal abstract fun popularFragment(): PopularFragment

    @FavoriteScope
    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    internal abstract fun favoriteFragment(): FavoriteFragment

    @DetailScope
    @ContributesAndroidInjector(modules = [DetailModule::class])
    internal abstract fun detailFragment(): DetailFragment

    @LocationScope
    @ContributesAndroidInjector(modules = [LocationModule::class])
    internal abstract fun locationFragment(): LocationFragment

}
