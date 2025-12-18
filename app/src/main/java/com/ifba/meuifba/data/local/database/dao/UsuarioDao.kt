package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE id = :usuarioId")
    suspend fun getUsuarioById(usuarioId: Long): Usuario?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUsuarioByEmail(email: String): Usuario?

    @Query("SELECT * FROM usuarios")
    fun getAllUsuarios(): Flow<List<Usuario>>

    @Query("SELECT * FROM usuarios WHERE tipo_usuario = :tipo")
    fun getUsuariosByTipo(tipo: String): Flow<List<Usuario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario): Long

    @Update
    suspend fun updateUsuario(usuario: Usuario)

    @Delete
    suspend fun deleteUsuario(usuario: Usuario)

    @Query("UPDATE usuarios SET foto_perfil = :fotoPerfil WHERE id = :usuarioId")
    suspend fun updateFotoPerfil(usuarioId: Long, fotoPerfil: String)

    @Query("UPDATE usuarios SET senha = :novaSenha WHERE id = :usuarioId")
    suspend fun updateSenha(usuarioId: Long, novaSenha: String)

    @Query("SELECT COUNT(*) FROM usuarios WHERE email = :email")
    suspend fun emailExists(email: String): Int
}