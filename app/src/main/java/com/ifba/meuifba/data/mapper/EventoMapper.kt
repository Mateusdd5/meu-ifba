package com.ifba.meuifba.data.mapper

import com.ifba.meuifba.data.local.database.entities.*
import com.ifba.meuifba.data.remote.dto.*
import com.ifba.meuifba.domain.model.*
import java.text.SimpleDateFormat
import java.util.*

// ========== EVENTO ==========

// Entity -> Model
fun Evento.toModel(
    categoria: CategoriaEvento,
    usuario: Usuario
): EventoModel {
    return EventoModel(
        id = this.id,
        titulo = this.titulo,
        descricao = this.descricao,
        categoria = CategoriaModel(
            id = categoria.id,
            nome = categoria.nome,
            descricao = categoria.descricao,
            cor = "#6200EA",
            icone = "event"
        ),
        dataEvento = this.dataEvento,
        horarioInicio = this.horarioInicio,
        horarioFim = this.horarioFim,
        local = this.local,
        publicoAlvo = this.publicoAlvo,
        cargaHoraria = this.cargaHoraria,
        certificacao = this.certificacao,
        requisitos = this.requisitos,
        numeroVagas = this.numeroVagas,
        vagasDisponiveis = this.vagasDisponiveis,
        statusInscricao = this.statusInscricao,
        criador = UsuarioBasicoModel(
            id = usuario.id,
            nome = usuario.nome,
            fotoPerfil = usuario.fotoPerfil
        ),
        dataFormatada = formatDate(this.dataEvento),
        diaDaSemana = getDayOfWeek(this.dataEvento),
        duracao = calculateDuration(this.horarioInicio, this.horarioFim),
        isFuturo = this.dataEvento > System.currentTimeMillis(),
        isLotado = this.vagasDisponiveis == 0,
        vagasPercentual = if (this.numeroVagas > 0) {
            (this.numeroVagas - this.vagasDisponiveis).toFloat() / this.numeroVagas * 100f
        } else 0f
    )
}

// DTO -> Entity
fun EventoResponse.toEntity(): Evento {
    return Evento(
        id = this.id,
        titulo = this.titulo,
        descricao = this.descricao,
        categoriaId = this.categoria.id,
        dataEvento = this.dataEvento,
        horarioInicio = this.horarioInicio,
        horarioFim = this.horarioFim,
        local = this.local,
        publicoAlvo = this.publicoAlvo,
        cargaHoraria = this.cargaHoraria,
        certificacao = this.certificacao,
        requisitos = this.requisitos,
        numeroVagas = this.numeroVagas,
        vagasDisponiveis = this.vagasDisponiveis,
        statusInscricao = this.statusInscricao,
        usuarioCriadorId = this.usuarioCriador.id,
        dataCriacao = this.dataCriacao,
        dataAtualizacao = this.dataAtualizacao
    )
}

// DTO -> Model (direto, sem salvar no banco)
fun EventoResponse.toModel(): EventoModel {
    return EventoModel(
        id = this.id,
        titulo = this.titulo,
        descricao = this.descricao,
        categoria = this.categoria.toModel(),
        dataEvento = this.dataEvento,
        horarioInicio = this.horarioInicio,
        horarioFim = this.horarioFim,
        local = this.local,
        publicoAlvo = this.publicoAlvo,
        cargaHoraria = this.cargaHoraria,
        certificacao = this.certificacao,
        requisitos = this.requisitos,
        numeroVagas = this.numeroVagas,
        vagasDisponiveis = this.vagasDisponiveis,
        statusInscricao = this.statusInscricao,
        criador = UsuarioBasicoModel(
            id = this.usuarioCriador.id,
            nome = this.usuarioCriador.nome,
            fotoPerfil = this.usuarioCriador.fotoPerfil
        ),
        dataFormatada = formatDate(this.dataEvento),
        diaDaSemana = getDayOfWeek(this.dataEvento),
        duracao = calculateDuration(this.horarioInicio, this.horarioFim),
        isFuturo = this.dataEvento > System.currentTimeMillis(),
        isLotado = this.vagasDisponiveis == 0,
        vagasPercentual = if (this.numeroVagas > 0) {
            (this.numeroVagas - this.vagasDisponiveis).toFloat() / this.numeroVagas * 100f
        } else 0f,
        imagemPrincipal = this.imagemBase64
    )
}

// ========== CATEGORIA EVENTO ==========

// Entity -> Model
fun CategoriaEvento.toModel(): CategoriaModel {
    return CategoriaModel(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        cor = "#6200EA",
        icone = "event"
    )
}

// DTO -> Entity
fun CategoriaEventoResponse.toEntity(): CategoriaEvento {
    return CategoriaEvento(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao
    )
}

// DTO -> Model
fun CategoriaEventoResponse.toModel(): CategoriaModel {
    return CategoriaModel(
        id = this.id,
        nome = this.nome,
        descricao = this.descricao,
        cor = "#6200EA",
        icone = "event"
    )
}

// ========== ESTATISTICA EVENTO ==========

// DTO -> Entity
fun EstatisticaEventoResponse.toEntity(): EstatisticaEvento {
    return EstatisticaEvento(
        id = this.id,
        eventoId = this.eventoId,
        numeroVisualizacoes = this.numeroVisualizacoes,
        numeroMarcacoes = this.numeroMarcacoes
    )
}

// ========== MIDIA EVENTO ==========

// DTO -> Entity
fun MidiaEventoResponse.toEntity(): MidiaEvento {
    return MidiaEvento(
        id = this.id,
        eventoId = this.eventoId,
        tipoMidia = this.tipoMidia,
        urlArquivo = this.url,
        nomeArquivo = this.nomeArquivo
    )
}

// ========== FUNÇÕES AUXILIARES ==========

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    return sdf.format(Date(timestamp))
}

private fun getDayOfWeek(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEEE", Locale("pt", "BR"))
    return sdf.format(Date(timestamp)).replaceFirstChar { it.uppercase() }
}

private fun calculateDuration(inicio: String, fim: String): String {
    return try {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateInicio = sdf.parse(inicio)
        val dateFim = sdf.parse(fim)

        if (dateInicio != null && dateFim != null) {
            val diff = dateFim.time - dateInicio.time
            val hours = diff / (1000 * 60 * 60)
            val minutes = (diff % (1000 * 60 * 60)) / (1000 * 60)

            when {
                hours > 0 && minutes > 0 -> "${hours}h${minutes}min"
                hours > 0 -> "${hours}h"
                else -> "${minutes}min"
            }
        } else "N/A"
    } catch (e: Exception) {
        "N/A"
    }
}