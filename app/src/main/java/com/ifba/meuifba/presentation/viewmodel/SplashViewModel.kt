package com.ifba.meuifba.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ifba.meuifba.utils.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    fun isAuthenticated(): Boolean = preferencesManager.isAuthenticated()
}