package com.ifba.meuifba.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EventoResponse(
    @SerializedName("id")
    val id: Long,

    @SerializedName("titulo")
    val titulo: String,

    @SerializedName("descricao")
    val descricao: String,

    @SerializedName("dataEvento")
    val dataEvento: Long,

    @SerializedName("horarioInicio")
    val horarioInicio: String,

    @SerializedName("horarioFim")
    val horarioFim: String,

    @SerializedName("local")
    val local: String,

    @SerializedName("publicoAlvo")
    val publicoAlvo: String,

    @SerializedName("cargaHoraria")
    val cargaHoraria: Int,

    @SerializedName("certificacao")
    val certificacao: Boolean,

    @SerializedName("requisitos")
    val requisitos: String?,

    @SerializedName("numeroVagas")
    val numeroVagas: Int,

    @SerializedName("vagasDisponiveis")
    val vagasDisponiveis: Int,

    @SerializedName("statusInscricao")
    val statusInscricao: String,

    @SerializedName("categoria")
    val categoria: CategoriaEventoResponse,

    @SerializedName("usuarioCriador")
    val usuarioCriador: UsuarioResponse,

    @SerializedName("dataCriacao")
    val dataCriacao: Long,

    @SerializedName("dataAtualizacao")
    val dataAtualizacao: Long
)