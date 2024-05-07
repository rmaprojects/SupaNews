package com.rmaprojects.newssupabaseapp.data.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.RemoteDataSource
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.data.source.remote.tables.SupabaseTables
import com.rmaprojects.newssupabaseapp.domain.model.News
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import com.rmaprojects.newssupabaseapp.utils.saveToLocalPreference
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresListDataFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import javax.inject.Inject

class SupaNewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val client: SupabaseClient
) : SupaNewsRepository {

    private val newsFeedChannel = client.channel("newsFeed")

    override suspend fun getAllNews(): Result<Flow<List<NewsEntity>>> {
        val data = newsFeedChannel.postgresListDataFlow(
            schema = "public",
            table = SupabaseTables.NEWS_TABLE,
            primaryKey = NewsEntity::id
        ).flowOn(Dispatchers.IO)

        newsFeedChannel.subscribe()

        return Result.success(data)
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

    override suspend fun unsubscribeChannel() {
        newsFeedChannel.unsubscribe()
        client.realtime.removeChannel(newsFeedChannel)
    }

    override fun registerUser(
        username: String,
        email: String,
        password: String,
    ): Flow<ResponseState<LocalUser>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val result = remoteDataSource.registerUser(username, email, password)
                if (result != null) {
                    val registeredUser =
                        remoteDataSource.getUserFromTable(result.id).saveToLocalPreference()
                    emit(ResponseState.Success(registeredUser))
                } else {
                    emit(ResponseState.Error("Register User error"))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override fun loginUser(email: String, password: String): Flow<ResponseState<LocalUser>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val result = remoteDataSource.loginUser(email, password)
                if (result != null) {
                    val loggedInUser =
                        remoteDataSource.getUserFromTable(result.id).saveToLocalPreference()
                    emit(ResponseState.Success(loggedInUser))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }
}