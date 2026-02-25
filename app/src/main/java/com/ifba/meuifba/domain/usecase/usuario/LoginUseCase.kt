package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.data.repository.UsuarioRepository
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(email: String, senha: String): Resource<UsuarioModel?> {
        return usuarioRepository.login(email, senha)
    }
}