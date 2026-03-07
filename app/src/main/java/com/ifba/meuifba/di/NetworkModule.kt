package com.ifba.meuifba.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ifba.meuifba.data.remote.AuthInterceptor
import com.ifba.meuifba.data.remote.api.EventoApi
import com.ifba.meuifba.data.remote.api.NotificacaoApi
import com.ifba.meuifba.data.remote.api.UsuarioApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideEventoApi(retrofit: Retrofit): EventoApi {
        return retrofit.create(EventoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsuarioApi(retrofit: Retrofit): UsuarioApi {
        return retrofit.create(UsuarioApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNotificacaoApi(retrofit: Retrofit): NotificacaoApi {
        return retrofit.create(NotificacaoApi::class.java)
    }
}