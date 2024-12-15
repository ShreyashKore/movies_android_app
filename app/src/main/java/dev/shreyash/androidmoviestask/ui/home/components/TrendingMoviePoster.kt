package dev.shreyash.androidmoviestask.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.shreyash.androidmoviestask.data.models.Movie


@Composable
fun TrendingMoviePoster(
    srNo: Int,
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    height: Dp = 300.dp,
) {
    Box {
        AsyncImage(
            "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .height(height)
                .aspectRatio(2 / 3f)
                .clickable { onMovieClick(movie.id) },
            contentScale = ContentScale.Crop
        )
        Text(
            "$srNo",
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 8.dp, vertical = 2.dp)
                .align(Alignment.BottomStart)
        )
    }
}