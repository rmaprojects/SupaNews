package com.rmaprojects.newssupabaseapp.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsEntity(
    @SerialName("author_id") val authorId: String,
    @SerialName("author_username") val authorUsername: String,
    @SerialName("title") val titleNews: String,
    @SerialName("content") val contentNews: String,
    @SerialName("header_img_url") val headerImgNews: String,
    @SerialName("id") val newsId: Int? = null,
    @SerialName("created_at") val createdAt: String? = null
)