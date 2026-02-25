package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.repository.MarcacaoParticipacaoRepository
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class MarcarParticipacaoUseCase @Inject constructor(
    private val marcacaoRepository: MarcacaoParticipacaoRepository
) {
    suspend operator fun invoke(usuarioId: Long, eventoId: Long): Resource<Unit> {
        return marcacaoRepository.marcarParticipacao(usuarioId, eventoId)
    }
}