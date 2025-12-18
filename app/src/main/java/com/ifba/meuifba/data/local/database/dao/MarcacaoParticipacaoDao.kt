package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.MarcacaoParticipacao
import kotlinx.coroutines.flow.Flow

@Dao
interface MarcacaoParticipacaoDao {
    @Query("SELECT * FROM marcacoes_participacao WHERE usuario_id = :usuarioId ORDER BY data_marcacao DESC")
    fun getMarcacoesByUsuario(usuarioId: Long): Flow<List<MarcacaoParticipacao>>

    @Query("SELECT * FROM marcacoes_participacao WHERE evento_id = :eventoId")
    fun getMarcacoesByEvento(eventoId: Long): Flow<List<MarcacaoParticipacao>>

    @Query("SELECT * FROM marcacoes_participacao WHERE usuario_id = :usuarioId AND evento_id = :eventoId LIMIT 1")
    suspend fun getMarcacao(usuarioId: Long, eventoId: Long): MarcacaoParticipacao?

    @Query("SELECT COUNT(*) FROM marcacoes_participacao WHERE evento_id = :eventoId")
    suspend fun countMarcacoesByEvento(eventoId: Long): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMarcacao(marcacao: MarcacaoParticipacao): Long

    @Update
    suspend fun updateMarcacao(marcacao: MarcacaoParticipacao)

    @Delete
    suspend fun deleteMarcacao(marcacao: MarcacaoParticipacao)

    @Query("DELETE FROM marcacoes_participacao WHERE usuario_id = :usuarioId AND evento_id = :eventoId")
    suspend fun deleteMarcacaoByIds(usuarioId: Long, eventoId: Long)

    @Query("SELECT EXISTS(SELECT 1 FROM marcacoes_participacao WHERE usuario_id = :usuarioId AND evento_id = :eventoId)")
    suspend fun isMarcado(usuarioId: Long, eventoId: Long): Boolean
}