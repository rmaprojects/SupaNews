package com.rmaprojects.newssupabaseapp.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsArticleDto(
    @SerialName("news_id") val id: Int,
    @SerialName("author_id") val authorId: String,
    @SerialName("news_author_name") val authorUsername: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("author_img") val authorImgUrl: String,
    @SerialName("news_image") val newsImgUrl: String,
    @SerialName("news_title") val newsTitle: String,
    @SerialName("news_content") val newsContent: String,
)