package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "eventos",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuario_criador_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoriaEvento::class,
            parentColumns = ["id"],
            childColumns = ["categoria_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["usuario_criador_id"]),
        Index(value = ["categoria_id"]),
        Index(value = ["data_evento"])
    ]
)
data class Evento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "usuario_criador_id")
    val usuarioCriadorId: Long,

    @ColumnInfo(name = "categoria_id")
    val categoriaId: Long,

    val titulo: String,
    val descricao: String,

    @ColumnInfo(name = "data_evento")
    val dataEvento: Long, // timestamp

    @ColumnInfo(name = "horario_inicio")
    val horarioInicio: String, // formato "HH:mm"

    @ColumnInfo(name = "horario_fim")
    val horarioFim: String,

    val local: String,

    @ColumnInfo(name = "publico_alvo")
    val publicoAlvo: String,

    @ColumnInfo(name = "carga_horaria")
    val cargaHoraria: Int,

    val certificacao: Boolean,

    val requisitos: String? = null,

    @ColumnInfo(name = "numero_vagas")
    val numeroVagas: Int,

    @ColumnInfo(name = "vagas_disponiveis")
    val vagasDisponiveis: Int,

    @ColumnInfo(name = "status_inscricao")
    val statusInscricao: String, // "abertas", "encerradas", "em_breve"

    @ColumnInfo(name = "data_criacao")
    val dataCriacao: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "data_atualizacao")
    val dataAtualizacao: Long = System.currentTimeMillis()
)