package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MarcacaoParticipacaoResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("usuarioId")
    val usuarioId: Long,

    @SerializedName("eventoId")
    val eventoId: Long,

    @SerializedName("dataMarcacao")
    val dataMarcacao: Long,

    @SerializedName("statusParticipacao")
    val statusParticipacao: String
)