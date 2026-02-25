package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventosPorCategoriaUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    operator fun invoke(categoriaId: Long): Flow<Resource<List<EventoModel>>> {
        return eventoRepository.getEventosByCategoria(categoriaId)
    }
}