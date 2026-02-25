package com.ifba.meuifba.domain.usecase.notificacao

import com.ifba.meuifba.data.repository.NotificacaoRepository
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class MarcarTodasNotificacoesLidasUseCase @Inject constructor(
    private val notificacaoRepository: NotificacaoRepository
) {
    suspend operator fun invoke(usuarioId: Long): Resource<Unit> {
        return notificacaoRepository.marcarTodasComoLidas(usuarioId)
    }
}