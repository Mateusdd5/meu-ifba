package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.Evento
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {
    @Query("SELECT * FROM eventos ORDER BY data_evento ASC")
    fun getAllEventos(): Flow<List<Evento>>

    @Query("SELECT * FROM eventos WHERE id = :eventoId")
    suspend fun getEventoById(eventoId: Long): Evento?

    @Query("SELECT * FROM eventos WHERE categoria_id = :categoriaId ORDER BY data_evento ASC")
    fun getEventosByCategoria(categoriaId: Long): Flow<List<Evento>>

    @Query("SELECT * FROM eventos WHERE usuario_criador_id = :usuarioId ORDER BY data_criacao DESC")
    fun getEventosByCriador(usuarioId: Long): Flow<List<Evento>>

    @Query("""
        SELECT * FROM eventos 
        WHERE titulo LIKE '%' || :query || '%' 
        OR descricao LIKE '%' || :query || '%'
        ORDER BY data_evento ASC
    """)
    fun searchEventos(query: String): Flow<List<Evento>>

    @Query("""
        SELECT * FROM eventos 
        WHERE data_evento >= :dataInicio 
        AND data_evento <= :dataFim
        ORDER BY data_evento ASC
    """)
    fun getEventosByPeriodo(dataInicio: Long, dataFim: Long): Flow<List<Evento>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvento(evento: Evento): Long

    @Update
    suspend fun updateEvento(evento: Evento)

    @Delete
    suspend fun deleteEvento(evento: Evento)

    @Query("UPDATE eventos SET vagas_disponiveis = :vagas WHERE id = :eventoId")
    suspend fun updateVagasDisponiveis(eventoId: Long, vagas: Int)

    @Query("UPDATE eventos SET status_inscricao = :status WHERE id = :eventoId")
    suspend fun updateStatusInscricao(eventoId: Long, status: String)

    @Query("SELECT * FROM eventos WHERE data_evento > :hoje ORDER BY data_evento ASC")
    fun getEventosFuturos(hoje: Long = System.currentTimeMillis()): Flow<List<Evento>>
}