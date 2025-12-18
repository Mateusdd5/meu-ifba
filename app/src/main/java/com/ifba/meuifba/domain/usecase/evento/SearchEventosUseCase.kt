package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchEventosUseCase @Inject constructor(
    // private val eventoRepository: EventoRepository
) {

    operator fun invoke(query: String): Flow<Resource<List<EventoModel>>> {
        TODO("Implementar quando EventoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    operator fun invoke(query: String): Flow<Resource<List<EventoModel>>> {
        // Validar query
        if (query.isBlank()) {
            return flow { emit(Resource.Success(emptyList())) }
        }

        // Buscar eventos
        return eventoRepository.searchEventos(query.trim())
    }
    */
}