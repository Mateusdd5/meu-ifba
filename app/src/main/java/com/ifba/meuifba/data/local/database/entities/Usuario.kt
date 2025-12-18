package com.ifba.meuifba.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuarios",
    foreignKeys = [
        ForeignKey(
            entity = Curso::class,
            parentColumns = ["id"],
            childColumns = ["curso_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["email"], unique = true), Index(value = ["curso_id"])]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nome: String,

    @ColumnInfo(name = "email")
    val email: String,

    val senha: String, // Hash da senha

    @ColumnInfo(name = "tipo_usuario")
    val tipoUsuario: String, // visitante, usuario_autenticado, organizador, administrador

    @ColumnInfo(name = "foto_perfil")
    val fotoPerfil: String? = null,

    @ColumnInfo(name = "curso_id")
    val cursoId: Long,

    @ColumnInfo(name = "data_cadastro")
    val dataCadastro: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "status_conta")
    val statusConta: String = "ativo" // ativo, inativo
)