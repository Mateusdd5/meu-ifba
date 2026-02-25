package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CursoResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("areaConhecimento")
    val areaConhecimento: AreaConhecimentoResponse
)