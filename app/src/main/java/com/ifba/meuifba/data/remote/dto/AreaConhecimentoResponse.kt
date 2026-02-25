package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AreaConhecimentoResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("descricao")
    val descricao: String?
)