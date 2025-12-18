package com.ifba.meuifba.domain.model

data class NotificacaoModel(
    val id: Long,
    val mensagem: String,
    val dataEnvio: Long,
    val visualizada: Boolean,
    val tipoNotificacao: String,

    // Evento relacionado
    val evento: EventoBasicoModel,

    // Campos derivados
    val tempoRelativo: String, // "há 2 horas", "há 3 dias"
    val dataFormatada: String,
    val icone: String, // Para exibir ícone diferente por tipo
    val corDestaque: String // Cor do badge por tipo
)

data class EventoBasicoModel(
    val id: Long,
    val titulo: String,
    val dataEvento: Long,
    val local: String,
    val imagemPrincipal: String?
)