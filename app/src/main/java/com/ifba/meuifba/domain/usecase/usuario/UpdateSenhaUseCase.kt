package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.data.repository.UsuarioRepository
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class UpdateSenhaUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(
        usuarioId: Long,
        senhaAtual: String,
        novaSenha: String
    ): Resource<Unit> {
        // TODO: Implementar no Repository quando API tiver o endpoint
        return Resource.Success(Unit)
    }
}