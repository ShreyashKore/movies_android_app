package dev.shreyash.androidmoviestask.data.models

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CastMember(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePath: String?,
    val character: String
)
