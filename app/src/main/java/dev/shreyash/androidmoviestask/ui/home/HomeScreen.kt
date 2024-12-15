package dev.shreyash.androidmoviestask.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.shreyash.androidmoviestask.R
import dev.shreyash.androidmoviestask.data.models.Movie
import dev.shreyash.androidmoviestask.ui.components.ErrorMessageWithRetry
import dev.shreyash.androidmoviestask.ui.home.components.MoviePoster
import dev.shreyash.androidmoviestask.ui.home.components.TrendingMoviePoster


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), onMovieClick: (Int) -> Unit
) {
    HomeScreen(
        isLoading = viewModel.isLoading.collectAsState().value,
        exception = viewModel.exception.collectAsState().value,
        trendingMovies = viewModel.trendingMovies.collectAsState().value,
        nowPlayingMovies = viewModel.nowPlayingMovies.collectAsState().value,
        popularMovies = viewModel.popularMovies.collectAsState().value,
        topRatedMovies = viewModel.topRatedMovies.collectAsState().value,
        upcomingMovies = viewModel.upcomingMovies.collectAsState().value,
        onMovieClick = onMovieClick,
        onRetry = viewModel::fetchMovies
    )
}


@Composable
fun HomeScreen(
    isLoading: Boolean,
    exception: Exception?,
    trendingMovies: List<Movie>,
    nowPlayingMovies: List<Movie>,
    popularMovies: List<Movie>,
    topRatedMovies: List<Movie>,
    upcomingMovies: List<Movie>,
    onMovieClick: (id: Int) -> Unit,
    onRetry: () -> Unit,
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold { innerPadding ->
        if (isLoading || exception != null) {
            Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    ErrorMessageWithRetry(
                        title = stringResource(R.string.error_occurred),
                        message = exception?.message ?: "Unknown error",
                        onRetry = onRetry
                    )
                }
            }
            return@Scaffold
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(12.dp),
        ) {
            Text(
                stringResource(R.string.what_do_you_want_to_watch),
                style = MaterialTheme.typography.titleSmall,
            )
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                Text(
                    stringResource(R.string.trending),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
                LazyRow {
                    itemsIndexed(trendingMovies.take(5)) { i, movie ->
                        TrendingMoviePoster(i + 1, movie, onMovieClick)
                    }
                }
            }

            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 8.dp,
                modifier = Modifier.padding(vertical = 8.dp),
            ) {
                HomeTabs.entries.forEachIndexed { index, tab ->
                    Tab(text = { Text(stringResource(tab.title)) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index })
                }
            }
            MovieVerticalGrid(
                movies = when (selectedTab) {
                    0 -> nowPlayingMovies
                    1 -> popularMovies
                    2 -> topRatedMovies
                    3 -> upcomingMovies
                    else -> throw IllegalStateException("Invalid tab")
                }, onMovieClick = onMovieClick
            )
        }
    }
}


@Composable
private fun MovieVerticalGrid(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    Column {
        movies.chunked(3).forEach { chunk ->
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                chunk.forEach { movie ->
                    MoviePoster(
                        movie = movie,
                        onMovieClick = onMovieClick,
                        modifier = Modifier.weight(1f),
                    )
                }
                if (chunk.size < 3) {
                    repeat(3 - chunk.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}


private enum class HomeTabs(@StringRes val title: Int) {
    NOW_PLAYING(R.string.now_playing),
    POPULAR(R.string.popular),
    TOP_RATED(R.string.top_rated),
    UPCOMING(R.string.upcoming),
}