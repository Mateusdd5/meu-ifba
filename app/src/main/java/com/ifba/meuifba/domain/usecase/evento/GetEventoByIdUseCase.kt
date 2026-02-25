package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.domain.model.EventoModel
import javax.inject.Inject

class GetEventoByIdUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    suspend operator fun invoke(eventoId: Long): EventoModel? {
        return eventoRepository.getEventoById(eventoId)
    }
}