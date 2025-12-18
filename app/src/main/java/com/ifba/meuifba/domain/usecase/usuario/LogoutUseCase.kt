package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.utils.PreferencesManager
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {

    suspend operator fun invoke(): Resource<Unit> {
        return try {
            // Limpar dados de login salvos
            preferencesManager.clearLoginData()

            // Se tiver token no backend, invalidar também
            // backendApi.logout()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao fazer logout")
        }
    }
}