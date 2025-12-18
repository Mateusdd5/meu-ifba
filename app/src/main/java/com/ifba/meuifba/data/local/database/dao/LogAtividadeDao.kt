package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.LogAtividade
import kotlinx.coroutines.flow.Flow

@Dao
interface LogAtividadeDao {
    @Query("SELECT * FROM logs_atividade ORDER BY data_hora DESC LIMIT :limit")
    fun getRecentLogs(limit: Int = 100): Flow<List<LogAtividade>>

    @Query("SELECT * FROM logs_atividade WHERE usuario_id = :usuarioId ORDER BY data_hora DESC")
    fun getLogsByUsuario(usuarioId: Long): Flow<List<LogAtividade>>

    @Query("SELECT * FROM logs_atividade WHERE acao = :acao ORDER BY data_hora DESC")
    fun getLogsByAcao(acao: String): Flow<List<LogAtividade>>

    @Query("""
        SELECT * FROM logs_atividade 
        WHERE data_hora >= :dataInicio AND data_hora <= :dataFim
        ORDER BY data_hora DESC
    """)
    fun getLogsByPeriodo(dataInicio: Long, dataFim: Long): Flow<List<LogAtividade>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: LogAtividade): Long

    @Query("DELETE FROM logs_atividade WHERE data_hora < :dataLimite")
    suspend fun deleteOldLogs(dataLimite: Long)
}