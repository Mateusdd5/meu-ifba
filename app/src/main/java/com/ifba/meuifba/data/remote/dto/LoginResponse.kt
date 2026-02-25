package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("usuario")
    val usuario: UsuarioResponse,

    @SerializedName("token")
    val token: String
)