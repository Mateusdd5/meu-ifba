package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventosMarcadosUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    operator fun invoke(usuarioId: Long): Flow<Resource<List<EventoModel>>> {
        return eventoRepository.getEventosMarcados(usuarioId)
    }
}