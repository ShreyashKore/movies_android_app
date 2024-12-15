package dev.shreyash.androidmoviestask.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    val id: Int,
    val title: String,
    val overview: String?,
    @SerialName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<Genre>,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path")  val backdropPath: String?,
    val status: String,
    val budget: Long?,
    val revenue: Long?
)

@Serializable
data class Genre(
    val id: Int,
    val name: String
)
