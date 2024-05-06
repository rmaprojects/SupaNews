package com.rmaprojects.newssupabaseapp.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
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
class AuthViewModel @Inject constructor(
    private val useCases: SupaNewsUseCases
) : ViewModel() {

    private val _loginState = MutableSharedFlow<ResponseState<LocalUser>>()
    val loginState = _loginState.asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    private val _registerState = MutableSharedFlow<ResponseState<LocalUser>>()
    val registerState = _registerState.asSharedFlow()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed(5000))

    fun performLogin(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _loginState.emitAll(useCases.loginUseCase(email, password))
        }
    }

    fun performRegister(
        username: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _registerState.emitAll(useCases.registerUseCase(username, email, password))
        }
    }

}