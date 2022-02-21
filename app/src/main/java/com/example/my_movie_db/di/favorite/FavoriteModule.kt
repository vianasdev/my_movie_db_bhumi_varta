package com.example.my_movie_db.di.favorite

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.ViewModel
import com.example.my_movie_db.di.ViewModelKey
import com.example.my_movie_db.fragment.favorite.FavoriteViewModel
import com.example.my_movie_db.fragment.favorite.datasource.FavoriteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher

@Module
abstract class FavoriteModule {
    companion object {
        @FavoriteScope
        @Provides
        internal fun provideDataSource(db: SQLiteDatabase, ioDispatcher: CoroutineDispatcher) =
            FavoriteDataSource(db, ioDispatcher)
    }

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindViewModel(viewModel: FavoriteViewModel): ViewModel
}