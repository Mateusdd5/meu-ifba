package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.data.repository.UsuarioRepository
import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class UpdateUsuarioUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(
        usuarioId: Long,
        nome: String,
        cursoId: Long
    ): Resource<UsuarioModel?> {
        return usuarioRepository.updateUsuario(usuarioId, nome, cursoId)
    }
}