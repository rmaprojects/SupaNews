package com.rmaprojects.newssupabaseapp.presentation.screens.addnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsEntity
import com.rmaprojects.newssupabaseapp.domain.usecases.SupaNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewsViewModel @Inject constructor(
    private val useCases: SupaNewsUseCases
): ViewModel() {

    private val _insertState =
        MutableSharedFlow<ResponseState<Boolean>>()
    val insertState = _insertState.asSharedFlow()
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000)
        )

    fun insertNewNews(
        news: NewsEntity
    ) {
        viewModelScope.launch {
            _insertState.emitAll(useCases.addNewNews(news))
        }
    }

}