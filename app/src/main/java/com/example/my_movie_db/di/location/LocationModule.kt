package com.example.my_movie_db.di.location

import androidx.lifecycle.ViewModel
import com.example.my_movie_db.di.ViewModelKey
import com.example.my_movie_db.fragment.location.LocationViewModel
import com.example.my_movie_db.fragment.location.datasource.LocationApi
import com.example.my_movie_db.fragment.location.datasource.LocationDatasource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
abstract class LocationModule {
    companion object {
        @LocationScope
        @Provides
        internal fun provideApi(retrofit: Retrofit): LocationApi =
            retrofit.create(LocationApi::class.java)

        @LocationScope
        @Provides
        internal fun provideDataSource(
            api: LocationApi,
            ioDispatcher: CoroutineDispatcher
        ): LocationDatasource = LocationDatasource(api, ioDispatcher)
    }

    @Binds
    @IntoMap
    @ViewModelKey(LocationViewModel::class)
    abstract fun bindViewModel(viewModel: LocationViewModel): ViewModel
}