package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cursos",
    foreignKeys = [
        ForeignKey(
            entity = AreaConhecimento::class,
            parentColumns = ["id"],
            childColumns = ["area_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["area_id"])]
)
data class Curso(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "nome_curso")
    val nomeCurso: String,

    @ColumnInfo(name = "area_id")
    val areaId: Long
)