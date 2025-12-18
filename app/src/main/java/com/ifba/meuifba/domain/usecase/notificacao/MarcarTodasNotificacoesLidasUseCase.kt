package com.ifba.meuifba.domain.usecase.notificacao

import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class MarcarTodasNotificacoesLidasUseCase @Inject constructor(
    // private val notificacaoRepository: NotificacaoRepository
) {

    suspend operator fun invoke(usuarioId: Long): Resource<Unit> {
        TODO("Implementar quando NotificacaoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    // - Marcar todas as notificações do usuário como lidas
    // - Usado no botão "Marcar todas como lidas"
    */
}