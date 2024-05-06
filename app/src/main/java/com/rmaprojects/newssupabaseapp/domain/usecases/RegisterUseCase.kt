package com.rmaprojects.newssupabaseapp.domain.usecases

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.newssupabaseapp.data.source.local.LocalUser
import com.rmaprojects.newssupabaseapp.domain.repository.SupaNewsRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
    private val repository: SupaNewsRepository
) {
    operator fun invoke(
        username: String,
        email: String,
        password: String
    ): Flow<ResponseState<LocalUser>> {
        return repository.registerUser(username, email, password)
    }
}