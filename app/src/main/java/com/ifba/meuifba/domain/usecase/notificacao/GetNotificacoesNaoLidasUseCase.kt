package com.ifba.meuifba.domain.usecase.notificacao

import com.ifba.meuifba.data.repository.NotificacaoRepository
import com.ifba.meuifba.domain.model.NotificacaoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificacoesNaoLidasUseCase @Inject constructor(
    private val notificacaoRepository: NotificacaoRepository
) {
    operator fun invoke(usuarioId: Long): Flow<Resource<List<NotificacaoModel>>> {
        return notificacaoRepository.getNotificacoesNaoLidas(usuarioId)
    }
}