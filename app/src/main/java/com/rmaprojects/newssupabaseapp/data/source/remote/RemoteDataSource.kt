package com.rmaprojects.newssupabaseapp.data.source.remote

import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.data.source.remote.model.UsersEntity
import com.rmaprojects.newssupabaseapp.data.source.remote.tables.SupabaseTables
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val client: SupabaseClient
) {

    suspend fun loginUser(
        email: String,
        password: String
    ): UserInfo? {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
        return client.auth.currentUserOrNull()
    }

    suspend fun registerUser(
        username: String,
        email: String,
        password: String,
    ): UserInfo? {
        return client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject {
                put("username", username)
            }
        }
    }

    suspend fun getAllNews(): List<NewsEntity> {
        return client.postgrest[SupabaseTables.NEWS_TABLE].select().decodeList()
    }

    suspend fun insertNews(
        newNews: NewsEntity
    ): PostgrestResult {
        return client.postgrest[SupabaseTables.NEWS_TABLE].insert(newNews)
    }

    fun retrieveLocalUser(): UserInfo? {
        return client.auth.currentUserOrNull()
    }

    suspend fun getUserFromTable(uuid: String): UsersEntity {
        return client.postgrest[SupabaseTables.USER_TABLE].select {
            filter {
                UsersEntity::uuid eq uuid
            }
        }.decodeSingle()
    }

}