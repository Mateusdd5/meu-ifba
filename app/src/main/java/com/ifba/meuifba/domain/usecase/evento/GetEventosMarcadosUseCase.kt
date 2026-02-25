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
        // TODO: Implementar no Repository quando API estiver pronta
        // Por enquanto retorna lista vazia
        return kotlinx.coroutines.flow.flow {
            emit(Resource.Loading())
            emit(Resource.Success(emptyList()))
        }
    }
}