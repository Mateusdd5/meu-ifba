package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class GetUsuarioByIdUseCase @Inject constructor(
    // private val usuarioRepository: UsuarioRepository
) {

    suspend operator fun invoke(usuarioId: Long): Resource<UsuarioModel> {
        TODO("Implementar quando UsuarioRepository estiver pronto")
    }

    /*
    // Implementação futura:
    suspend operator fun invoke(usuarioId: Long): Resource<UsuarioModel> {
        return try {
            val usuario = usuarioRepository.getUsuarioById(usuarioId)

            if (usuario != null) {
                Resource.Success(usuario)
            } else {
                Resource.Error("Usuário não encontrado")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao buscar usuário")
        }
    }
    */
}