package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventosPorCategoriaUseCase @Inject constructor(
    // private val eventoRepository: EventoRepository
) {

    operator fun invoke(categoriaId: Long): Flow<Resource<List<EventoModel>>> {
        TODO("Implementar quando EventoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    operator fun invoke(categoriaId: Long): Flow<Resource<List<EventoModel>>> {
        return flow {
            emit(Resource.Loading())

            try {
                eventoRepository.getEventosByCategoria(categoriaId).collect { eventos ->
                    // Filtrar apenas eventos futuros
                    val eventosFuturos = eventos.filter { it.isFuturo }

                    // Ordenar por data
                    val eventosOrdenados = eventosFuturos.sortedBy { it.dataEvento }

                    emit(Resource.Success(eventosOrdenados))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Erro ao buscar eventos"))
            }
        }
    }
    */
}