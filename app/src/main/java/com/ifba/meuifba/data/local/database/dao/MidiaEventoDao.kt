package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.MidiaEvento
import kotlinx.coroutines.flow.Flow

@Dao
interface MidiaEventoDao {
    @Query("SELECT * FROM midias_evento WHERE evento_id = :eventoId")
    fun getMidiasByEvento(eventoId: Long): Flow<List<MidiaEvento>>

    @Query("SELECT * FROM midias_evento WHERE evento_id = :eventoId AND tipo_midia = :tipo")
    fun getMidiasByTipo(eventoId: Long, tipo: String): Flow<List<MidiaEvento>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMidia(midia: MidiaEvento): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMidias(midias: List<MidiaEvento>)

    @Delete
    suspend fun deleteMidia(midia: MidiaEvento)

    @Query("DELETE FROM midias_evento WHERE evento_id = :eventoId")
    suspend fun deleteAllMidiasByEvento(eventoId: Long)
}