package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.domain.model.UsuarioModel
import com.ifba.meuifba.utils.Resource
import com.ifba.meuifba.utils.ValidationUtils
import javax.inject.Inject

class UpdateUsuarioUseCase @Inject constructor(
    // private val usuarioRepository: UsuarioRepository
) {

    suspend operator fun invoke(
        usuarioId: Long,
        nome: String,
        cursoId: Long,
        fotoPerfil: String? = null
    ): Resource<UsuarioModel> {
        TODO("Implementar quando UsuarioRepository estiver pronto")
    }

    /*
    // Implementação futura:
    // - Validar nome (não vazio, tamanho mínimo)
    // - Atualizar dados
    // - Retornar usuário atualizado
    */
}