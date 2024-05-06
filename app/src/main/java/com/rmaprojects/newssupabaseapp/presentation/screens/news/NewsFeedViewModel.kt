package com.rmaprojects.newssupabaseapp.presentation.screens.news

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.data.source.remote.tables.SupabaseTables
import com.rmaprojects.newssupabaseapp.domain.model.News
import com.rmaprojects.newssupabaseapp.domain.usecases.SupaNewsUseCases
import com.rmaprojects.newssupabaseapp.utils.mapToNews
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.decodeOldRecord
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val useCases: SupaNewsUseCases,
    private val channel: RealtimeChannel
) : ViewModel() {

    private val _newsFeedState = MutableStateFlow<ResponseState<Boolean>>(
        ResponseState.Idle
    )

    val newsFeedState = _newsFeedState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ResponseState.Idle
    )

    val news = mutableStateListOf<News>()

    fun fetchNews() {
        viewModelScope.launch {
            useCases.fetchNewsFeed().collect {
                when (it) {
                    is ResponseState.Error -> _newsFeedState.emit(it)
                    is ResponseState.Idle -> _newsFeedState.emit(it)
                    is ResponseState.Loading -> _newsFeedState.emit(it)
                    is ResponseState.Success -> {
                        news.addAll(it.data)
                        _newsFeedState.emit(ResponseState.Success(true))
                    }
                }
            }
        }
    }

    fun connectToRealtime() {
        viewModelScope.launch {
            channel.postgresChangeFlow<PostgresAction>("public") {
                table = SupabaseTables.NEWS_TABLE
            }.onEach { action ->
                when (action) {
                    is PostgresAction.Delete -> news.removeIf { it.newsId == action.decodeOldRecord<NewsEntity>().mapToNews().newsId }
                    is PostgresAction.Insert -> news + action.decodeRecord<NewsEntity>().mapToNews()
                    is PostgresAction.Select -> error("Select Not applicable")
                    is PostgresAction.Update -> error("Select Not applicable")
                }
            }.launchIn(viewModelScope)

            channel.subscribe()
        }
    }

}