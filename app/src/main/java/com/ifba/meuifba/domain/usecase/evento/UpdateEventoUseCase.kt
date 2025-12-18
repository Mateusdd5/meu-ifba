package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import com.ifba.meuifba.utils.ValidationUtils
import javax.inject.Inject

class UpdateEventoUseCase @Inject constructor(
    // private val eventoRepository: EventoRepository
) {

    suspend operator fun invoke(
        eventoId: Long,
        titulo: String,
        descricao: String,
        categoriaId: Long,
        dataEvento: Long,
        horarioInicio: String,
        horarioFim: String,
        local: String,
        publicoAlvo: String,
        cargaHoraria: Int,
        certificacao: Boolean,
        requisitos: String?,
        numeroVagas: Int
    ): Resource<EventoModel> {
        TODO("Implementar quando EventoRepository estiver pronto")
    }

    /*
    // Implementação futura com validações
    */
}