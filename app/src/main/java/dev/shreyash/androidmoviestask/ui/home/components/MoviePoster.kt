package dev.shreyash.androidmoviestask.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.shreyash.androidmoviestask.data.models.Movie


@Composable
fun MoviePoster(
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        "https://image.tmdb.org/t/p/w500${movie.posterPath}",
        contentDescription = movie.title,
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(2 / 3f)
            .clickable { onMovieClick(movie.id) },
        contentScale = ContentScale.Crop
    )
}
