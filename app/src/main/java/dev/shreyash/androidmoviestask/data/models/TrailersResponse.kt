package dev.shreyash.androidmoviestask.data.models

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TrailersResponse(
    val id: Int,
    val results: List<Trailer>
)