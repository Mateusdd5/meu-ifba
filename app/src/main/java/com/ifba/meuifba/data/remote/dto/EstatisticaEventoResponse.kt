package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EstatisticaEventoResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("eventoId")
    val eventoId: Long,

    @SerializedName("numeroVisualizacoes")
    val numeroVisualizacoes: Int,

    @SerializedName("numeroMarcacoes")
    val numeroMarcacoes: Int,

    @SerializedName("numeroCompartilhamentos")
    val numeroCompartilhamentos: Int,

    @SerializedName("dataUltimaAtualizacao")
    val dataUltimaAtualizacao: Long
)