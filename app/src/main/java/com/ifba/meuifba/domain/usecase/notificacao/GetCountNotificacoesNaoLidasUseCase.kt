package com.ifba.meuifba.domain.usecase.notificacao

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountNotificacoesNaoLidasUseCase @Inject constructor(
    // private val notificacaoRepository: NotificacaoRepository
) {

    operator fun invoke(usuarioId: Long): Flow<Int> {
        TODO("Implementar quando NotificacaoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    // - Retornar COUNT de notificações não lidas
    // - Flow: atualiza automaticamente quando nova notificação chega
    // - Usado para badge no ícone de notificações
    */
}