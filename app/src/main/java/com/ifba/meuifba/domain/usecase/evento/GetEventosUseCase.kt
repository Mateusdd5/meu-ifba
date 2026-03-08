package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventosUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    operator fun invoke(usuarioId: Long = -1L): Flow<Resource<List<EventoModel>>> {
        return eventoRepository.getEventos(usuarioId)
    }
}