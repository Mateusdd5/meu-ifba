package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchEventosUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<EventoModel>>> {
        return eventoRepository.searchEventos(query)
    }
}