package dev.shreyash.androidmoviestask.ui.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shreyash.androidmoviestask.data.models.CastMember
import dev.shreyash.androidmoviestask.data.models.MovieDetailsResponse
import dev.shreyash.androidmoviestask.domain.MoviesRepository
import dev.shreyash.androidmoviestask.domain.Res
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [MovieDetailsScreen]
 *
 * Showcases usage of [HiltViewModel] and [SavedStateHandle].
 *
 * Also uses [Res] classes to handle success, error and loading states.
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MoviesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId = savedStateHandle.get<String>("movieId")?.toIntOrNull() ?: -1

    private val _movieDetails = MutableStateFlow<Res<MovieDetailsResponse>>(Res.Loading())
    val movieDetails: StateFlow<Res<MovieDetailsResponse>> = _movieDetails

    private val _castDetails = MutableStateFlow<Res<List<CastMember>>>(Res.Loading())
    val castDetails: StateFlow<Res<List<CastMember>>> = _castDetails

    init {
        fetchMovieDetails()
        fetchCastDetails()
    }


    fun fetchMovieDetails() {
        viewModelScope.launch {
            _movieDetails.update {
                try {
                    Res.Success(repository.getMovieDetails(movieId))
                } catch (e: Exception) {
                    Res.Error(e)
                }
            }
        }
    }

    fun fetchCastDetails() {
        viewModelScope.launch {
            _castDetails.update {
                try {
                    Res.Success(repository.getCastDetails(movieId).cast)
                } catch (e: Exception) {
                    println("EEEEE $e")
                    Res.Error(e)
                }
            }
        }
    }
}