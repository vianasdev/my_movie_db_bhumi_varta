package com.example.my_movie_db.di.home

import androidx.lifecycle.ViewModel
import com.example.my_movie_db.di.ViewModelKey
import com.example.my_movie_db.fragment.home.HomeViewModel
import com.example.my_movie_db.fragment.home.datasource.HomeApi
import com.example.my_movie_db.fragment.home.datasource.HomeDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
abstract class HomeModule {
    companion object {
        @HomeScope
        @Provides
        internal fun provideApi(retrofit: Retrofit): HomeApi =
            retrofit.create(HomeApi::class.java)

        @HomeScope
        @Provides
        internal fun provideDataSource(
            api: HomeApi,
            ioDispatcher: CoroutineDispatcher
        ): HomeDataSource = HomeDataSource(api, ioDispatcher)
    }

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}
