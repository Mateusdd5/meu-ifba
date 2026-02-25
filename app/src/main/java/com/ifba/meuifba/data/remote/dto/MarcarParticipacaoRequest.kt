package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MarcarParticipacaoRequest(
    @SerializedName("usuarioId")
    val usuarioId: Long,

    @SerializedName("eventoId")
    val eventoId: Long
)