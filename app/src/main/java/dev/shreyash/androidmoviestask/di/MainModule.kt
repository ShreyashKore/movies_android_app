package dev.shreyash.androidmoviestask.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.shreyash.androidmoviestask.BuildConfig
import dev.shreyash.androidmoviestask.data.AuthInterceptor
import dev.shreyash.androidmoviestask.data.TmdbApi
import dev.shreyash.androidmoviestask.domain.MoviesRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

/**
 * Main Module for providing dependencies.
 * For now all the dependencies are provided here.
 */
@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun providesTmdbApi(): TmdbApi {
        val json = Json { ignoreUnknownKeys = true }
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(BuildConfig.API_KEY, BuildConfig.BEARER_TOKEN))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
        return retrofit.create(TmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun providesMoviesRepository(tmdbApi: TmdbApi): MoviesRepository {
        return MoviesRepository(tmdbApi)
    }

}