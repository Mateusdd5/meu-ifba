package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "marcacoes_participacao",
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
        Index(value = ["usuario_id", "evento_id"], unique = true)
    ]
)
data class MarcacaoParticipacao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "usuario_id")
    val usuarioId: Long,

    @ColumnInfo(name = "evento_id")
    val eventoId: Long,

    @ColumnInfo(name = "data_marcacao")
    val dataMarcacao: Long = System.currentTimeMillis(),

    val status: String = "interessado" // interessado, compareceu, nao_compareceu
)