package com.rmaprojects.newssupabaseapp.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsEntity(
    @SerialName("author_username") val authorUsername: String,
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
    @SerialName("id") val id: Int? = null,
    @SerialName("author_id") val authorId: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("header_img_url") val headerImgUrl: String? = null
)