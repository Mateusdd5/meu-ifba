package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.remote.dto.UpdateEventoRequest
import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class UpdateEventoUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
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
        numeroVagas: Int,
        imagemBase64: String? = null
    ): Resource<EventoModel?> {
        val request = UpdateEventoRequest(
            titulo = titulo,
            descricao = descricao,
            categoriaId = categoriaId,
            dataEvento = dataEvento,
            horarioInicio = horarioInicio,
            horarioFim = horarioFim,
            local = local,
            publicoAlvo = publicoAlvo,
            cargaHoraria = cargaHoraria,
            certificacao = certificacao,
            requisitos = requisitos,
            numeroVagas = numeroVagas,
            imagemBase64 = imagemBase64
        )
        return eventoRepository.updateEvento(eventoId, request)
    }
}