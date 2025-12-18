package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notificacoes",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Evento::class,
            parentColumns = ["id"],
            childColumns = ["evento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["usuario_id"]),
        Index(value = ["evento_id"]),
        Index(value = ["data_envio"])
    ]
)
data class Notificacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Long,

    @ColumnInfo(name = "evento_id")
    val eventoId: Long,

    val mensagem: String,

    @ColumnInfo(name = "data_envio")
    val dataEnvio: Long = System.currentTimeMillis(),

    val visualizada: Boolean = false,

    @ColumnInfo(name = "tipo_notificacao")
    val tipoNotificacao: String // "recomendacao", "lembrete", "atualizacao"
)