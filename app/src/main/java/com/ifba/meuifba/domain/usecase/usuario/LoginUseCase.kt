package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.Resource
import com.ifba.meuifba.utils.ValidationUtils
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    // private val usuarioRepository: UsuarioRepository,
    // private val preferencesManager: PreferencesManager
) {

    suspend operator fun invoke(
        email: String,
        senha: String,
        rememberMe: Boolean = false
    ): Resource<UsuarioModel> {
        TODO("Implementar quando UsuarioRepository estiver pronto")
    }

    /*
    // Implementação futura:
    suspend operator fun invoke(
        email: String,
        senha: String,
        rememberMe: Boolean = false
    ): Resource<UsuarioModel> {
        return try {
            // Validações
            if (!ValidationUtils.isValidEmail(email)) {
                return Resource.Error("E-mail inválido")
            }

            if (!ValidationUtils.isIfbaEmail(email)) {
                return Resource.Error("Use seu e-mail institucional @ifba.edu.br")
            }

            if (!ValidationUtils.isValidPassword(senha)) {
                return Resource.Error("Senha inválida")
            }

            // Tentar login
            val resultado = usuarioRepository.login(email, senha)

            if (resultado is Resource.Success) {
                // Salvar dados de login
                resultado.data?.let { usuario ->
                    preferencesManager.saveLoginData(
                        userId = usuario.id,
                        token = "TOKEN_AQUI", // Será token JWT do backend
                        rememberMe = rememberMe
                    )
                }
            }

            resultado
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao fazer login")
        }
    }
    */
}