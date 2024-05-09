package com.rmaprojects.newssupabaseapp.presentation.screens.read

import com.rmaprojects.newssupabaseapp.domain.usecases.SupaNewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadNewsViewModel @Inject constructor(
    private val useCases: SupaNewsUseCases
) {
}