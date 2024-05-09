package com.rmaprojects.newssupabaseapp.data.repository

import android.util.Log
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.RemoteDataSource
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsArticleDto
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.data.source.remote.tables.SupabaseTables
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import com.rmaprojects.newssupabaseapp.utils.saveToLocalPreference
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import io.github.jan.supabase.realtime.postgresSingleDataFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Response
import javax.inject.Inject

class SupaNewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val client: SupabaseClient
) : SupaNewsRepository {

    private val newsFeedChannel = client.channel("newsFeed")
    private val newsDetailChannel = client.channel("newsDetail")

    override suspend fun getAllNews(): Result<Flow<List<NewsEntity>>> {
        val data = newsFeedChannel.postgresListDataFlow(
            schema = "public",
            table = SupabaseTables.NEWS_TABLE,
            primaryKey = NewsEntity::id,
        ).flowOn(Dispatchers.IO)

        newsFeedChannel.subscribe()

        return Result.success(data)
    }

    override suspend fun getNewsArticle(newsId: Int): Flow<ResponseState<NewsArticleDto>> = flow {
        emit(ResponseState.Loading)
        try {
            emit(
                ResponseState.Success(
                    remoteDataSource.getNewsArticle(newsId)
                )
            )
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun insertNews(news: NewsEntity): Flow<ResponseState<Boolean>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val currentUser = remoteDataSource.retrieveLocalUser()
                if (currentUser != null) {
                    remoteDataSource.insertNews(news)
                    emit(ResponseState.Success(true))
                } else {
                    emit(ResponseState.Error("You haven't logged in, please log in"))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override suspend fun unsubscribeNewsFeedChannel() {
        newsFeedChannel.unsubscribe()
        client.realtime.removeChannel(newsFeedChannel)
    }

    override suspend fun unsubscribeNewsDetailChannel() {
        newsDetailChannel.unsubscribe()
        client.realtime.removeChannel(newsDetailChannel)
    }

    override fun registerUser(
        username: String,
        email: String,
        password: String,
    ): Flow<ResponseState<LocalUser>> =
        flow {
            emit(ResponseState.Loading)
            try {
                remoteDataSource.registerUser(username, email, password)?.let { userInfo ->
                    emit(
                        ResponseState.Success(
                            remoteDataSource.getUserFromTable(userInfo.id).saveToLocalPreference()
                        )
                    )
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
                Log.d("REGISTER_ERROR", e.toString())
            }
        }

    override fun loginUser(email: String, password: String): Flow<ResponseState<LocalUser>> =
        flow {
            emit(ResponseState.Loading)
            try {
                remoteDataSource.loginUser(email, password)?.let { userInfo ->
                    emit(
                        ResponseState.Success(
                            remoteDataSource.getUserFromTable(userInfo.id).saveToLocalPreference()

                        )
                    )
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
                Log.d("LOGIN_ERROR", e.toString())
            }
        }
}