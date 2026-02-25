package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.data.remote.dto.RegisterRequest
import com.ifba.meuifba.data.repository.UsuarioRepository
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(
        nome: String,
        email: String,
        senha: String,
        cursoId: Long
    ): Resource<UsuarioModel?> {
        val request = RegisterRequest(
            nome = nome,
            email = email,
            senha = senha,
            cursoId = cursoId
        )
        return usuarioRepository.register(request)
    }
}