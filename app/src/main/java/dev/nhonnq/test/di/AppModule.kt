package dev.nhonnq.test.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.nhonnq.test.BuildConfig
import dev.nhonnq.test.api.ApiHelper
import dev.nhonnq.test.api.ApiHelperImpl
import dev.nhonnq.test.api.ApiService
import dev.nhonnq.test.data.db.AppRoomDB
import dev.nhonnq.test.utils.NetworkConnectivity
import dev.nhonnq.test.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private var appRoomDB: AppRoomDB? = null
        private val LOCK = Any()
    }

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_API_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideAppRoomDB(application: Application): AppRoomDB {
        if (appRoomDB == null) {
            synchronized(LOCK) {
                if (appRoomDB == null) {
                    appRoomDB = Room.databaseBuilder(application, AppRoomDB::class.java, "AppRoom.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
        }
        return appRoomDB!!
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return NetworkHelper(context)
    }

}