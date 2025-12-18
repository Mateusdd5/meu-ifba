package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.PreferenciaUsuario
import kotlinx.coroutines.flow.Flow

@Dao
interface PreferenciaUsuarioDao {
    @Query("SELECT * FROM preferencias_usuario WHERE usuario_id = :usuarioId")
    fun getPreferenciasByUsuario(usuarioId: Long): Flow<List<PreferenciaUsuario>>

    @Query("SELECT categoria_id FROM preferencias_usuario WHERE usuario_id = :usuarioId")
    suspend fun getCategoriasPreferidas(usuarioId: Long): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPreferencia(preferencia: PreferenciaUsuario): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPreferencias(preferencias: List<PreferenciaUsuario>)

    @Delete
    suspend fun deletePreferencia(preferencia: PreferenciaUsuario)

    @Query("DELETE FROM preferencias_usuario WHERE usuario_id = :usuarioId")
    suspend fun deleteAllPreferenciasByUsuario(usuarioId: Long)

    @Query("DELETE FROM preferencias_usuario WHERE usuario_id = :usuarioId AND categoria_id = :categoriaId")
    suspend fun deletePreferenciaByCategoria(usuarioId: Long, categoriaId: Long)
}