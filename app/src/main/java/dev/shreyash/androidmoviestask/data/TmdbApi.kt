package dev.shreyash.androidmoviestask.data

import dev.shreyash.androidmoviestask.data.models.MovieDetailsResponse
import dev.shreyash.androidmoviestask.data.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1,
    ): MoviesResponse


    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1,
        @Query("region") region: String? = null
    ): MoviesResponse


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1,
    ): MoviesResponse


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1,
    ): MoviesResponse


    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1,
        @Query("region") region: String? = null,
    ): MoviesResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = "en-US"
    ): MovieDetailsResponse


    @GET("movie/{movie_id}/credits")
    suspend fun getCastDetails(
        @Path("movie_id") movieId: Int,
    ): Any


    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id") movieId: Int,
    ): Any
}
