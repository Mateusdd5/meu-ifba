package com.ifba.meuifba.domain.model

data class EventoModel(
    val id: Long,
    val titulo: String,
    val descricao: String,
    val dataEvento: Long,
    val horarioInicio: String,
    val horarioFim: String,
    val local: String,
    val publicoAlvo: String,
    val cargaHoraria: Int,
    val certificacao: Boolean,
    val requisitos: String?,
    val numeroVagas: Int,
    val vagasDisponiveis: Int,
    val statusInscricao: String,

    // Objetos relacionados (diferente da Entity que só tem IDs)
    val categoria: CategoriaModel,
    val criador: UsuarioBasicoModel,

    // Campos derivados/calculados
    val dataFormatada: String,
    val diaDaSemana: String,
    val duracao: String,
    val vagasPercentual: Float,
    val isLotado: Boolean,
    val isFuturo: Boolean,
    val isMarcado: Boolean = false,

    // Estatísticas
    val numeroVisualizacoes: Int = 0,
    val numeroMarcacoes: Int = 0,

    // Mídias
    val imagemPrincipal: String? = null,
    val midias: List<MidiaModel> = emptyList()
)

data class UsuarioBasicoModel(
    val id: Long,
    val nome: String,
    val fotoPerfil: String?
)

data class MidiaModel(
    val id: Long,
    val tipo: String,
    val url: String,
    val nome: String
)