package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MidiaEventoResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("eventoId")
    val eventoId: Long,

    @SerializedName("tipoMidia")
    val tipoMidia: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("nomeArquivo")
    val nomeArquivo: String,

    @SerializedName("dataUpload")
    val dataUpload: Long
)