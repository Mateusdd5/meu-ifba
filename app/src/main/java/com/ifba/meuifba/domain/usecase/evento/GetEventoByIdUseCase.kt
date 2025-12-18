package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class GetEventoByIdUseCase @Inject constructor(
    // private val eventoRepository: EventoRepository
) {

    suspend operator fun invoke(eventoId: Long): Resource<EventoModel> {
        TODO("Implementar quando EventoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    suspend operator fun invoke(eventoId: Long): Resource<EventoModel> {
        return try {
            val evento = eventoRepository.getEventoById(eventoId)
            if (evento != null) {
                Resource.Success(evento)
            } else {
                Resource.Error("Evento não encontrado")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro ao buscar evento")
        }
    }
    */
}