package dev.shreyash.androidmoviestask.ui.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import dev.shreyash.androidmoviestask.R
import dev.shreyash.androidmoviestask.data.models.CastMember
import dev.shreyash.androidmoviestask.data.models.MovieDetailsResponse
import dev.shreyash.androidmoviestask.data.models.Trailer
import dev.shreyash.androidmoviestask.domain.Res
import dev.shreyash.androidmoviestask.ui.components.ErrorMessageWithRetry

/**
 * Shows the movie details with cast members
 *
 * This screen showcases advance state management with loading and error state handling using [Res] classes.
 */
@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel(),
    onTrailerClick: (String) -> Unit,
    onBack: () -> Unit,
) {
    val movieDetails by movieDetailsViewModel.movieDetails.collectAsState()
    val cast by movieDetailsViewModel.castDetails.collectAsState()
    val trailers by movieDetailsViewModel.trailers.collectAsState()
    MovieDetailsScreen(
        movieDetailsState = movieDetails,
        castState = cast,
        trailersState = trailers,
        onTrailerClick = onTrailerClick,
        onRetry = movieDetailsViewModel::fetchMovieDetails,
        onRetryCast = movieDetailsViewModel::fetchCastDetails,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    movieDetailsState: Res<MovieDetailsResponse>,
    castState: Res<List<CastMember>>,
    trailersState: Res<List<Trailer>>,
    onTrailerClick: (String) -> Unit,
    onRetry: () -> Unit,
    onRetryCast: () -> Unit,
    onBack: () -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.details)) },
                navigationIcon = {
                    IconButton(onBack) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
        ) {

            if (movieDetailsState is Res.Loading) {
                CircularProgressIndicator()
                return@Column
            }
            if (movieDetailsState is Res.Error) {
                ErrorMessageWithRetry(
                    title = stringResource(R.string.error_occurred),
                    message = movieDetailsState.error.message,
                    onRetry = onRetry,
                )
                return@Column
            }

            val movieDetails = (movieDetailsState as Res.Success).data


            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        "https://image.tmdb.org/t/p/w500${movieDetails.backdropPath}",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(bottom = 60.dp)
                            .clickable {
                                val trailers = (trailersState as? Res.Success)?.data ?: return@clickable
                                val trailer = trailers.first {
                                    it.site.equals("youtube", true)
                                }
                                // currently only youtube videos are supported
                                // TODO: remove this logic once all kinds of videos are supported
                                onTrailerClick(trailer.key)
                            },
                        contentScale = ContentScale.Crop,
                    )
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        AsyncImage(
                            "https://image.tmdb.org/t/p/w500${movieDetails.posterPath}",
                            contentDescription = null,
                            modifier = Modifier
                                .height(180.dp)
                                .aspectRatio(2 / 3f)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.size(12.dp))
                        Text(
                            movieDetails.title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        val color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Release Date",
                            tint = color,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            movieDetails.releaseDate.substringBefore("-"),
                            style = MaterialTheme.typography.labelLarge.copy(color),
                            modifier = Modifier.padding(4.dp)
                        )
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Runtime",
                            tint = color,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            "${movieDetails.runtime} " + stringResource(R.string.minutes),
                            style = MaterialTheme.typography.labelLarge.copy(color),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    FlowRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                    ) {
                        movieDetails.genres.map {
                            Text(
                                it.name,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    MaterialTheme.colorScheme.onSecondary
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                    TabRow(selectedTabIndex = selectedTab) {
                        Tab(
                            text = { Text(stringResource(R.string.about_movie)) },
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                        )
                        Tab(
                            text = { Text(stringResource(R.string.cast)) },
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                        )
                    }
                    when (selectedTab) {
                        0 -> AboutMovieTab(movieDetails.overview)
                        1 -> CastTab(castState, onRetryCast, modifier = Modifier.height(500.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AboutMovieTab(description: String) {
    Text(
        description,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CastTab(
    castState: Res<List<CastMember>>,
    retry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (castState is Res.Loading) {
        CircularProgressIndicator(modifier)
        return
    }
    if (castState is Res.Error) {
        ErrorMessageWithRetry(
            title = stringResource(R.string.error_occurred),
            message = castState.error.message,
            onRetry = retry,
            modifier = modifier
        )
        return
    }

    val cast = (castState as Res.Success).data

    LazyVerticalGrid(GridCells.Fixed(2), modifier = modifier) {
        items(cast) { castMember ->
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    "https://image.tmdb.org/t/p/w500${castMember.profilePath}",
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Text(
                    castMember.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}