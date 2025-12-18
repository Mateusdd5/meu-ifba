package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.EstatisticaEvento
import kotlinx.coroutines.flow.Flow

@Dao
interface EstatisticaEventoDao {
    @Query("SELECT * FROM estatisticas_evento WHERE evento_id = :eventoId ORDER BY data_registro DESC LIMIT 1")
    suspend fun getEstatisticaByEvento(eventoId: Long): EstatisticaEvento?

    @Query("SELECT * FROM estatisticas_evento WHERE evento_id = :eventoId ORDER BY data_registro ASC")
    fun getHistoricoEstatisticas(eventoId: Long): Flow<List<EstatisticaEvento>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstatistica(estatistica: EstatisticaEvento): Long

    @Update
    suspend fun updateEstatistica(estatistica: EstatisticaEvento)

    @Query("UPDATE estatisticas_evento SET numero_visualizacoes = numero_visualizacoes + 1 WHERE evento_id = :eventoId")
    suspend fun incrementarVisualizacoes(eventoId: Long)

    @Query("UPDATE estatisticas_evento SET numero_marcacoes = numero_marcacoes + 1 WHERE evento_id = :eventoId")
    suspend fun incrementarMarcacoes(eventoId: Long)

    @Query("UPDATE estatisticas_evento SET numero_marcacoes = numero_marcacoes - 1 WHERE evento_id = :eventoId AND numero_marcacoes > 0")
    suspend fun decrementarMarcacoes(eventoId: Long)

    @Delete
    suspend fun deleteEstatistica(estatistica: EstatisticaEvento)
}