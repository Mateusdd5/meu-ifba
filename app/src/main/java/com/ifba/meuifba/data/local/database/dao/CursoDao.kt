package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.Curso
import kotlinx.coroutines.flow.Flow

@Dao
interface CursoDao {
    @Query("SELECT * FROM cursos")
    fun getAllCursos(): Flow<List<Curso>>

    @Query("SELECT * FROM cursos WHERE id = :cursoId")
    suspend fun getCursoById(cursoId: Long): Curso?

    @Query("SELECT * FROM cursos WHERE area_id = :areaId")
    fun getCursosByArea(areaId: Long): Flow<List<Curso>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurso(curso: Curso): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCursos(cursos: List<Curso>)

    @Update
    suspend fun updateCurso(curso: Curso)

    @Delete
    suspend fun deleteCurso(curso: Curso)
}