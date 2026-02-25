package com.ifba.meuifba.data.mapper

import com.ifba.meuifba.data.local.database.entities.Notificacao
import com.ifba.meuifba.data.remote.dto.NotificacaoResponse
import com.ifba.meuifba.domain.model.NotificacaoModel
import com.ifba.meuifba.domain.model.EventoBasicoModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// ========== NOTIFICACAO ==========

// Entity -> Model (requer informações do evento)
fun Notificacao.toModel(
    eventoTitulo: String,
    eventoData: Long,
    eventoLocal: String,
    eventoImagem: String?
): NotificacaoModel {
    return NotificacaoModel(
        id = this.id,
        mensagem = this.mensagem,
        dataEnvio = this.dataEnvio,
        visualizada = this.visualizada,
        tipoNotificacao = this.tipoNotificacao,
        evento = EventoBasicoModel(
            id = this.eventoId,
            titulo = eventoTitulo,
            dataEvento = eventoData,
            local = eventoLocal,
            imagemPrincipal = eventoImagem
        ),
        tempoRelativo = getTempoRelativo(this.dataEnvio),
        dataFormatada = formatDate(this.dataEnvio),
        icone = getIconePorTipo(this.tipoNotificacao),
        corDestaque = getCorPorTipo(this.tipoNotificacao)
    )
}

// DTO -> Entity
fun NotificacaoResponse.toEntity(): Notificacao {
    return Notificacao(
        id = this.id,
        mensagem = this.mensagem,
        dataEnvio = this.dataEnvio,
        visualizada = this.visualizada,
        tipoNotificacao = this.tipoNotificacao,
        eventoId = this.eventoId,
        usuarioId = this.usuarioId
    )
}

// DTO -> Model (com informações completas do backend)
fun NotificacaoResponse.toModel(
    eventoTitulo: String,
    eventoData: Long,
    eventoLocal: String,
    eventoImagem: String?
): NotificacaoModel {
    return NotificacaoModel(
        id = this.id,
        mensagem = this.mensagem,
        dataEnvio = this.dataEnvio,
        visualizada = this.visualizada,
        tipoNotificacao = this.tipoNotificacao,
        evento = EventoBasicoModel(
            id = this.eventoId,
            titulo = eventoTitulo,
            dataEvento = eventoData,
            local = eventoLocal,
            imagemPrincipal = eventoImagem
        ),
        tempoRelativo = getTempoRelativo(this.dataEnvio),
        dataFormatada = formatDate(this.dataEnvio),
        icone = getIconePorTipo(this.tipoNotificacao),
        corDestaque = getCorPorTipo(this.tipoNotificacao)
    )
}

// ========== FUNÇÕES AUXILIARES ==========

private fun getTempoRelativo(timestamp: Long): String {
    val agora = System.currentTimeMillis()
    val diff = agora - timestamp

    return when {
        diff < TimeUnit.MINUTES.toMillis(1) -> "Agora"
        diff < TimeUnit.HOURS.toMillis(1) -> {
            val minutos = TimeUnit.MILLISECONDS.toMinutes(diff)
            "Há ${minutos}min"
        }
        diff < TimeUnit.DAYS.toMillis(1) -> {
            val horas = TimeUnit.MILLISECONDS.toHours(diff)
            "Há ${horas}h"
        }
        diff < TimeUnit.DAYS.toMillis(7) -> {
            val dias = TimeUnit.MILLISECONDS.toDays(diff)
            "Há ${dias}d"
        }
        else -> formatDate(timestamp)
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
    return sdf.format(Date(timestamp))
}

private fun getIconePorTipo(tipo: String): String {
    return when (tipo.uppercase()) {
        "NOVO_EVENTO" -> "event"
        "LEMBRETE" -> "alarm"
        "ATUALIZACAO" -> "update"
        "CANCELAMENTO" -> "cancel"
        "CONFIRMACAO" -> "check_circle"
        else -> "notifications"
    }
}

private fun getCorPorTipo(tipo: String): String {
    return when (tipo.uppercase()) {
        "NOVO_EVENTO" -> "#6200EA"
        "LEMBRETE" -> "#FF6F00"
        "ATUALIZACAO" -> "#0091EA"
        "CANCELAMENTO" -> "#D32F2F"
        "CONFIRMACAO" -> "#00C853"
        else -> "#757575"
    }
}