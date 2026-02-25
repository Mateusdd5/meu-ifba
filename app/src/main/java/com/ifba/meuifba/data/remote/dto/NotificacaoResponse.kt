package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificacaoResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("mensagem")
    val mensagem: String,

    @SerializedName("dataEnvio")
    val dataEnvio: Long,

    @SerializedName("visualizada")
    val visualizada: Boolean,

    @SerializedName("tipoNotificacao")
    val tipoNotificacao: String,

    @SerializedName("eventoId")
    val eventoId: Long,

    @SerializedName("usuarioId")
    val usuarioId: Long
)