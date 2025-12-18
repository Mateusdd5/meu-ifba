package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "midias_evento",
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
data class MidiaEvento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "evento_id")
    val eventoId: Long,

    @ColumnInfo(name = "tipo_midia")
    val tipoMidia: String, // "imagem", "documento"

    @ColumnInfo(name = "url_arquivo")
    val urlArquivo: String,

    @ColumnInfo(name = "nome_arquivo")
    val nomeArquivo: String,

    @ColumnInfo(name = "data_upload")
    val dataUpload: Long = System.currentTimeMillis()
)