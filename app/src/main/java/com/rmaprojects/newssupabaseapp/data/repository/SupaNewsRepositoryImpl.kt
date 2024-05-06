package com.rmaprojects.newssupabaseapp.data.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.data.source.remote.RemoteDataSource
import com.rmaprojects.newssupabaseapp.domain.model.News
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import com.rmaprojects.newssupabaseapp.utils.mapToEntity
import com.rmaprojects.newssupabaseapp.utils.mapToNews
import com.rmaprojects.newssupabaseapp.utils.saveToLocalPreference
import io.github.jan.supabase.realtime.RealtimeChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SupaNewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : SupaNewsRepository {

    override fun getAllNews(): Flow<ResponseState<List<News>>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val result = remoteDataSource.getAllNews()
                if (result.isNotEmpty()) {
                    emit(ResponseState.Success(result.map { it.mapToNews() }))
                } else {
                    emit(ResponseState.Success(emptyList()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.toString()))
            }
        }

    override fun insertNews(news: News): Flow<ResponseState<Boolean>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val currentUser = remoteDataSource.retrieveLocalUser()
                if (currentUser != null) {
                    remoteDataSource.insertNews(news.mapToEntity(currentUser.id))
                    emit(ResponseState.Success(true))
                } else {
                    emit(ResponseState.Error("You haven't logged in, please log in"))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.toString()))
            }
        }

    override fun registerUser(
        username: String,
        email: String,
        password: String,
    ): Flow<ResponseState<LocalUser>> =
        flow {
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
                emit(ResponseState.Error(e.toString()))
            }
        }

    override fun loginUser(email: String, password: String): Flow<ResponseState<LocalUser>> =
        flow {
            try {
                val result = remoteDataSource.loginUser(email, password)
                if (result != null) {
                    val loggedInUser =
                        remoteDataSource.getUserFromTable(result.id).saveToLocalPreference()
                    emit(ResponseState.Success(loggedInUser))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.toString()))
            }
        }

    override val newsRealtimeChannel: RealtimeChannel
        get() = TODO("Not yet implemented")
}