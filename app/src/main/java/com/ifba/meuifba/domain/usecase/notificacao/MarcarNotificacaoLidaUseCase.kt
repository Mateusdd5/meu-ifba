package com.ifba.meuifba.domain.usecase.notificacao

import com.ifba.meuifba.data.repository.NotificacaoRepository
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class MarcarNotificacaoLidaUseCase @Inject constructor(
    private val notificacaoRepository: NotificacaoRepository
) {
    suspend operator fun invoke(notificacaoId: Long): Resource<Unit> {
        return notificacaoRepository.marcarComoLida(notificacaoId)
    }
}