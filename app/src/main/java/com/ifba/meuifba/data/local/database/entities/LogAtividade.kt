package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "logs_atividade",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuario_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["usuario_id"]),
        Index(value = ["data_hora"])
    ]
)
data class LogAtividade(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Long,

    val acao: String, // criar_evento, editar_evento, marcar_participacao, login, etc

    @ColumnInfo(name = "data_hora")
    val dataHora: Long = System.currentTimeMillis(),

    val descricao: String
)