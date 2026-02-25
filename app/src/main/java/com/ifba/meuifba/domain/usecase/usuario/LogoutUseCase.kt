package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.utils.PreferencesManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    suspend operator fun invoke() {
        preferencesManager.clearLoginData()
    }
}