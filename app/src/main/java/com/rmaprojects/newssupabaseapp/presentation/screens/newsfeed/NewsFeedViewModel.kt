package com.rmaprojects.newssupabaseapp.presentation.screens.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.domain.usecases.SupaNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val useCases: SupaNewsUseCases
) : ViewModel() {

    private val _newsFeedState = MutableStateFlow<ResponseState<List<NewsEntity>>>(
        ResponseState.Loading
    )
    val newsFeedState = _newsFeedState.asStateFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ResponseState.Loading
    )

    fun connectToRealtime() {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.newsFeedUseCases.fetchAllNews()
                .onSuccess { flow ->
                    flow.onEach {
                        _newsFeedState.emit(ResponseState.Success(it))
                    }.collect()
                }
                .onFailure {
                    _newsFeedState.emit(ResponseState.Error(it.message.toString()))
                }
        }
    }

    fun leaveRealtimeChannel() = viewModelScope.launch {
        useCases.newsFeedUseCases.unsubscribeChannel()
    }
}