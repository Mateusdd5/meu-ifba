package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.CategoriaEvento
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaEventoDao {
    @Query("SELECT * FROM categorias_evento")
    fun getAllCategorias(): Flow<List<CategoriaEvento>>

    @Query("SELECT * FROM categorias_evento WHERE id = :categoriaId")
    suspend fun getCategoriaById(categoriaId: Long): CategoriaEvento?

    @Query("SELECT * FROM categorias_evento WHERE nome = :nome LIMIT 1")
    suspend fun getCategoriaByNome(nome: String): CategoriaEvento?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoria(categoria: CategoriaEvento): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategorias(categorias: List<CategoriaEvento>)

    @Update
    suspend fun updateCategoria(categoria: CategoriaEvento)

    @Delete
    suspend fun deleteCategoria(categoria: CategoriaEvento)
}