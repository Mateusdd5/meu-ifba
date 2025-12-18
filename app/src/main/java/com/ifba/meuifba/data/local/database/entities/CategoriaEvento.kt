package com.ifba.meuifba.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorias_evento")
data class CategoriaEvento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nome: String,
    val descricao: String? = null
)