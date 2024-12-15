package dev.shreyash.androidmoviestask.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyash.androidmoviestask.data.models.Movie
import dev.shreyash.androidmoviestask.domain.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {
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

    private fun fetchMovies() {
        viewModelScope.launch {
            _trendingMovies.value = moviesRepository.getTrendingMovies("day").results.take(5)
            _nowPlayingMovies.value = moviesRepository.getNowPlayingMovies().results
            _popularMovies.value = moviesRepository.getPopularMovies().results
            _topRatedMovies.value = moviesRepository.getTopRatedMovies().results
            _upcomingMovies.value = moviesRepository.getUpcomingMovies().results
        }
    }
}