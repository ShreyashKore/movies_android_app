package dev.shreyash.androidmoviestask.domain

import dev.shreyash.androidmoviestask.data.TmdbApi
import dev.shreyash.androidmoviestask.data.models.CastMember
import dev.shreyash.androidmoviestask.data.models.MovieDetailsResponse
import dev.shreyash.androidmoviestask.data.models.MoviesResponse
import dev.shreyash.androidmoviestask.data.models.TrailersResponse
import kotlinx.serialization.Serializable

/**
 * Repository class for handling all the movie related operations.
 * Currently only redirects the calls to the [TmdbApi].
 *
 * Might be used for caching and other operations in future.
 */
class MoviesRepository(private val api: TmdbApi) {
    suspend fun getTrendingMovies(
        timeWindow: String, language: String? = "en-US", page: Int? = 1
    ): MoviesResponse {
        return api.getTrendingMovies(timeWindow, language, page)
    }

    suspend fun getNowPlayingMovies(
        language: String? = "en-US", page: Int? = 1, region: String? = null
    ): MoviesResponse {
        return api.getNowPlayingMovies(language, page, region)
    }

    suspend fun getPopularMovies(
        language: String? = "en-US", page: Int? = 1
    ): MoviesResponse {
        return api.getPopularMovies(language, page)
    }

    suspend fun getTopRatedMovies(
        language: String? = "en-US", page: Int? = 1
    ): MoviesResponse {
        return api.getTopRatedMovies(language, page)
    }

    suspend fun getUpcomingMovies(
        language: String? = "en-US", page: Int? = 1, region: String? = null
    ): MoviesResponse {
        return api.getUpcomingMovies(language, page, region)
    }

    suspend fun getMovieDetails(
        movieId: Int, language: String? = "en-US"
    ): MovieDetailsResponse {
        return api.getMovieDetails(movieId, language)
    }

    suspend fun getCastDetails(movieId: Int): CastResponse {
        return api.getCastDetails(movieId)
    }

    suspend fun getMovieTrailers(movieId: Int): TrailersResponse {
        return api.getMovieTrailers(movieId)
    }
}

@Serializable
data class CastResponse(
    val cast: List<CastMember>
)