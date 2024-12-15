package dev.shreyash.androidmoviestask.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Trailer(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val site: String,
    val size: Int,
    val type: String
)