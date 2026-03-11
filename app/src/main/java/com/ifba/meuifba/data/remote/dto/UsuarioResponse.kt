package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsuarioResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("tipoUsuario")
    val tipoUsuario: String,

    @SerializedName("fotoPerfil")
    val fotoPerfil: String?,

    @SerializedName("curso")
    val curso: CursoResponse?,  // ← era não nullable

    @SerializedName("dataCadastro")
    val dataCadastro: Long,

    @SerializedName("statusConta")
    val statusConta: String
)