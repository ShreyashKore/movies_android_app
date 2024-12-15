package dev.shreyash.androidmoviestask.ui.home

import android.net.http.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyash.androidmoviestask.data.models.Movie
import dev.shreyash.androidmoviestask.domain.MoviesRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _exception = MutableStateFlow<Exception?>(null)
    val exception = _exception.asStateFlow()

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies = _trendingMovies.asStateFlow()

    private val _nowPlayingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies = _topRatedMovies.asStateFlow()

    private val _upcomingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upcomingMovies = _upcomingMovies.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch(CoroutineExceptionHandler { _, e ->
            Log.e("HomeScreen", "$e")
        }) {
            try {
                _isLoading.value = true
                _exception.value = null
                val trendingMoviesDeferred = async { moviesRepository.getTrendingMovies("day").results.take(5) }
                val nowPlayingMoviesDeferred = async { moviesRepository.getNowPlayingMovies().results }
                val popularMoviesDeferred = async { moviesRepository.getPopularMovies().results }
                val topRatedMoviesDeferred = async { moviesRepository.getTopRatedMovies().results }
                val upcomingMoviesDeferred = async { moviesRepository.getUpcomingMovies().results }
                _trendingMovies.value = trendingMoviesDeferred.await()
                _nowPlayingMovies.value = nowPlayingMoviesDeferred.await()
                _popularMovies.value = popularMoviesDeferred.await()
                _topRatedMovies.value = topRatedMoviesDeferred.await()
                _upcomingMovies.value = upcomingMoviesDeferred.await()
            } catch (e: HttpException) {
                _exception.value = e
            } catch (e: IOException) {
                // can add retry mechanism here
                _exception.value = e
            } catch (e: Exception) {
                _exception.value = e
            } finally {
                _isLoading.value = false
            }
        }
    }
}