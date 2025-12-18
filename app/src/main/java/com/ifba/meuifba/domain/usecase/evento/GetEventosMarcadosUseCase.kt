package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventosMarcadosUseCase @Inject constructor(
    // private val marcacaoRepository: MarcacaoParticipacaoRepository,
    // private val eventoRepository: EventoRepository
) {

    operator fun invoke(usuarioId: Long): Flow<Resource<List<EventoModel>>> {
        TODO("Implementar quando Repositories estiverem prontos")
    }

    /*
    // Implementação futura:
    operator fun invoke(usuarioId: Long): Flow<Resource<List<EventoModel>>> {
        return flow {
            emit(Resource.Loading())

            try {
                // Buscar marcações do usuário
                marcacaoRepository.getMarcacoesByUsuario(usuarioId).collect { marcacoes ->
                    // Para cada marcação, buscar evento completo
                    val eventos = marcacoes.map { marcacao ->
                        eventoRepository.getEventoById(marcacao.eventoId)
                    }.filterNotNull()

                    // Ordenar por data (mais próximo primeiro)
                    val eventosOrdenados = eventos.sortedBy { it.dataEvento }

                    emit(Resource.Success(eventosOrdenados))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Erro ao buscar eventos marcados"))
            }
        }
    }
    */
}