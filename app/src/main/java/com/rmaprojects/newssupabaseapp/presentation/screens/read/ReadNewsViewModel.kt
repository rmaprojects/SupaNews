package com.rmaprojects.newssupabaseapp.presentation.screens.read

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.remote.model.NewsArticleDto
import com.rmaprojects.newssupabaseapp.domain.usecases.SupaNewsUseCases
import com.rmaprojects.newssupabaseapp.presentation.screens.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadNewsViewModel @Inject constructor(
    private val useCases: SupaNewsUseCases,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs: ReadNewsArguments = stateHandle.navArgs()

    private val _newsDetailState = MutableStateFlow<ResponseState<NewsArticleDto>>(
        ResponseState.Idle
    )
    val newsDetailState = _newsDetailState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun fetchNews() {
        viewModelScope.launch {
            _newsDetailState.emitAll(
                useCases.fetchNewsUseCase(navArgs.newsId)
            )
        }
    }
}