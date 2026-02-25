package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class DeleteEventoUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    suspend operator fun invoke(eventoId: Long): Resource<Unit> {
        return eventoRepository.deleteEvento(eventoId)
    }
}