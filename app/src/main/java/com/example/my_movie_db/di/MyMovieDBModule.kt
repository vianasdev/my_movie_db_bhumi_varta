package com.example.my_movie_db.di

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.my_movie_db.BuildConfig
import com.example.my_movie_db.contract.HttpContract
import com.example.my_movie_db.databases.DatabaseHandler
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class MyMovieDBModule {
    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .connectTimeout(3 * 60, TimeUnit.SECONDS)
            .readTimeout(3 * 60, TimeUnit.SECONDS)
            .writeTimeout(3 * 60, TimeUnit.SECONDS)
            .connectionSpecs(listOf(ConnectionSpec.COMPATIBLE_TLS))
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(HttpContract.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    internal fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    internal fun provideSQLiteDB(context: Context): SQLiteDatabase = DatabaseHandler(context.applicationContext).readableDatabase
}