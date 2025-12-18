package com.ifba.meuifba.domain.usecase.usuario

import com.ifba.meuifba.utils.Resource
import com.ifba.meuifba.utils.ValidationUtils
import javax.inject.Inject

class UpdateSenhaUseCase @Inject constructor(
    // private val usuarioRepository: UsuarioRepository
) {

    suspend operator fun invoke(
        usuarioId: Long,
        senhaAtual: String,
        novaSenha: String,
        confirmarNovaSenha: String
    ): Resource<Unit> {
        TODO("Implementar quando UsuarioRepository estiver pronto")
    }

    /*
    // Implementação futura:
    // - Validar senha atual (verificar hash)
    // - Validar nova senha (força, comprimento)
    // - Verificar se senhas conferem
    // - Atualizar senha (hashear nova senha)
    */
}