package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "estatisticas_evento",
    foreignKeys = [
        ForeignKey(
            entity = Evento::class,
            parentColumns = ["id"],
            childColumns = ["evento_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["evento_id"])]
)
data class EstatisticaEvento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "evento_id")
    val eventoId: Long,

    @ColumnInfo(name = "numero_visualizacoes")
    val numeroVisualizacoes: Int = 0,

    @ColumnInfo(name = "numero_marcacoes")
    val numeroMarcacoes: Int = 0,

    @ColumnInfo(name = "data_registro")
    val dataRegistro: Long = System.currentTimeMillis()
)