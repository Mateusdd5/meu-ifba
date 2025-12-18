package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.Notificacao
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificacaoDao {
    @Query("SELECT * FROM notificacoes WHERE usuario_id = :usuarioId ORDER BY data_envio DESC")
    fun getNotificacoesByUsuario(usuarioId: Long): Flow<List<Notificacao>>

    @Query("SELECT * FROM notificacoes WHERE usuario_id = :usuarioId AND visualizada = 0 ORDER BY data_envio DESC")
    fun getNotificacoesNaoLidas(usuarioId: Long): Flow<List<Notificacao>>

    @Query("SELECT COUNT(*) FROM notificacoes WHERE usuario_id = :usuarioId AND visualizada = 0")
    fun countNotificacoesNaoLidas(usuarioId: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotificacao(notificacao: Notificacao): Long

    @Update
    suspend fun updateNotificacao(notificacao: Notificacao)

    @Query("UPDATE notificacoes SET visualizada = 1 WHERE id = :notificacaoId")
    suspend fun marcarComoLida(notificacaoId: Long)

    @Query("UPDATE notificacoes SET visualizada = 1 WHERE usuario_id = :usuarioId")
    suspend fun marcarTodasComoLidas(usuarioId: Long)

    @Delete
    suspend fun deleteNotificacao(notificacao: Notificacao)
}