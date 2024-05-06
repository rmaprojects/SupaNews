package com.rmaprojects.newssupabaseapp.utils

import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.data.source.remote.model.UsersEntity
import com.rmaprojects.newssupabaseapp.domain.model.News

fun NewsEntity.mapToNews(): News {
    return News(
        this.titleNews,
        this.contentNews,
        this.headerImgNews,
        this.authorUsername,
        this.authorId,
        this.newsId
    )
}

fun News.mapToEntity(userUuid: String): NewsEntity {
    val username = LocalUser.username ?: ""
    return NewsEntity(
        userUuid,
        username,
        this.title,
        this.content,
        this.headerImgUrl
    )
}

fun UsersEntity.saveToLocalPreference(): LocalUser {
    return LocalUser.apply {
        this.bio = this@saveToLocalPreference.bio ?: ""
        this.imageUrl = this@saveToLocalPreference.imageUrl
        this.username = this@saveToLocalPreference.username
    }
}