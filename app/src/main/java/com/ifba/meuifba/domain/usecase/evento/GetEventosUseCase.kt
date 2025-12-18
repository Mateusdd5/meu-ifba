package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventosUseCase @Inject constructor(
    // Repository será injetado aqui depois
    // private val eventoRepository: EventoRepository
) {

    // Por enquanto, retorna Flow vazio
    // Depois implementaremos com Repository real
    operator fun invoke(): Flow<Resource<List<EventoModel>>> {
        TODO("Implementar quando EventoRepository estiver pronto")
    }

    /*
    // Implementação futura:
    operator fun invoke(): Flow<Resource<List<EventoModel>>> {
        return eventoRepository.getEventos()
    }
    */
}