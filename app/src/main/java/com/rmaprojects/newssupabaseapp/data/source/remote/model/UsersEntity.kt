package com.rmaprojects.newssupabaseapp.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersEntity(
    @SerialName("id") val uuid: String,
    @SerialName("username") val username: String,
    @SerialName("bio") val bio: String? = "",
    @SerialName("image_url") val imageUrl: String? = null,
)
