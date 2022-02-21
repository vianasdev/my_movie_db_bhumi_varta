package com.example.my_movie_db.di.popular

import androidx.lifecycle.ViewModel
import com.example.my_movie_db.di.ViewModelKey
import com.example.my_movie_db.fragment.popular.PopularViewModel
import com.example.my_movie_db.fragment.popular.datasource.PopularApi
import com.example.my_movie_db.fragment.popular.datasource.PopularDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
abstract class PopularModule {
    companion object {
        @PopularScope
        @Provides
        internal fun provideApi(retrofit: Retrofit): PopularApi =
            retrofit.create(PopularApi::class.java)

        @PopularScope
        @Provides
        internal fun provideDataSource(
            api: PopularApi,
            ioDispatcher: CoroutineDispatcher
        ): PopularDataSource = PopularDataSource(api, ioDispatcher)
    }

    @Binds
    @IntoMap
    @ViewModelKey(PopularViewModel::class)
    abstract fun bindViewModel(viewModel: PopularViewModel):ViewModel
}