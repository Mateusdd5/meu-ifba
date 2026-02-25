package com.ifba.meuifba.domain.usecase.notificacao

import com.ifba.meuifba.data.repository.NotificacaoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountNotificacoesNaoLidasUseCase @Inject constructor(
    private val notificacaoRepository: NotificacaoRepository
) {
    operator fun invoke(usuarioId: Long): Flow<Int> {
        return notificacaoRepository.getCountNaoLidas(usuarioId)
    }
}