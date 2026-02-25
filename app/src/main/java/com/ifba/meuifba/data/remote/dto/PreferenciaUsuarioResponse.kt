package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PreferenciaUsuarioResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("usuarioId")
    val usuarioId: Long,

    @SerializedName("categoriaId")
    val categoriaId: Long,

    @SerializedName("dataCriacao")
    val dataCriacao: Long
)