package com.ifba.meuifba.domain.usecase.evento

import com.ifba.meuifba.data.remote.dto.CreateEventoRequest
import com.ifba.meuifba.data.repository.EventoRepository
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject

class CreateEventoUseCase @Inject constructor(
    private val eventoRepository: EventoRepository
) {
    suspend operator fun invoke(
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
        usuarioCriadorId: Long
    ): Resource<EventoModel?> {
        val request = CreateEventoRequest(
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
            usuarioCriadorId = usuarioCriadorId
        )
        return eventoRepository.createEvento(request)
    }
}