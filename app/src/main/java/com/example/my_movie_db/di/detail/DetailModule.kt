package com.example.my_movie_db.di.detail

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModel
import com.example.my_movie_db.di.ViewModelKey
import com.example.my_movie_db.fragment.detail.DetailViewModel
import com.example.my_movie_db.fragment.detail.datasource.DetailApi
import com.example.my_movie_db.fragment.detail.datasource.DetailDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
abstract class DetailModule {
    companion object{
        @DetailScope
        @Provides
        internal fun provideApi(retrofit: Retrofit):DetailApi = retrofit.create(DetailApi::class.java)

        @DetailScope
        @Provides
        internal fun provideDataSource(api: DetailApi,db:SQLiteDatabase,ioDispatcher: CoroutineDispatcher) = DetailDataSource(api, db, ioDispatcher)
    }

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindViewModel(viewModel: DetailViewModel):ViewModel
}