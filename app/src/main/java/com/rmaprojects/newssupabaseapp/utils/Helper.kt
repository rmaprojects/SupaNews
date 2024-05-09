package com.rmaprojects.newssupabaseapp.utils

import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.data.source.remote.model.UsersEntity
import com.rmaprojects.newssupabaseapp.domain.model.News
import kotlinx.serialization.json.JsonObject

fun UsersEntity.saveToLocalPreference(): LocalUser {
    return LocalUser.apply {
        this.uuid = this@saveToLocalPreference.id
        this.bio = this@saveToLocalPreference.bio ?: ""
        this.imageUrl = this@saveToLocalPreference.imageUrl
        this.username = this@saveToLocalPreference.username
    }
}