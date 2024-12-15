package dev.shreyash.androidmoviestask.data.models

import kotlinx.serialization.Serializable

@Serializable
data class TrailersResponse(
    val id: Int,
    val results: List<Trailer>
)