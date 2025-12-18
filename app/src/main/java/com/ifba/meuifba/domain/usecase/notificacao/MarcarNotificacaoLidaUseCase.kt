package com.ifba.meuifba.domain.usecase.notificacao

import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class MarcarNotificacaoLidaUseCase @Inject constructor(
    // private val notificacaoRepository: NotificacaoRepository
) {

    suspend operator fun invoke(notificacaoId: Long): Resource<Unit> {
        TODO("Implementar quando NotificacaoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    // - Atualizar notificacao.visualizada = true
    // - Usado quando usuário clica na notificação
    */
}