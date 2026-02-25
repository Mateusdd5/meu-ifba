package com.ifba.meuifba.data.mapper

import com.ifba.meuifba.data.local.database.entities.MarcacaoParticipacao
import com.ifba.meuifba.data.remote.dto.MarcacaoParticipacaoResponse

// ========== MARCACAO PARTICIPACAO ==========

// DTO -> Entity
fun MarcacaoParticipacaoResponse.toEntity(): MarcacaoParticipacao {
    return MarcacaoParticipacao(
        id = this.id,
        usuarioId = this.usuarioId,
        eventoId = this.eventoId,
        dataMarcacao = this.dataMarcacao,
        status = this.statusParticipacao
    )
}