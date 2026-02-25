package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.data.repository.UsuarioRepository
import com.ifba.meuifba.domain.model.UsuarioModel
import javax.inject.Inject

class GetUsuarioByIdUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(usuarioId: Long): UsuarioModel? {
        return usuarioRepository.getUsuarioById(usuarioId)
    }
}