package com.ifba.meuifba.data.local.database.dao

import androidx.room.*
import com.ifba.meuifba.data.local.database.entities.AreaConhecimento
import kotlinx.coroutines.flow.Flow

@Dao
interface AreaConhecimentoDao {
    @Query("SELECT * FROM areas_conhecimento")
    fun getAllAreas(): Flow<List<AreaConhecimento>>

    @Query("SELECT * FROM areas_conhecimento WHERE id = :areaId")
    suspend fun getAreaById(areaId: Long): AreaConhecimento?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(area: AreaConhecimento): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAreas(areas: List<AreaConhecimento>)

    @Update
    suspend fun updateArea(area: AreaConhecimento)

    @Delete
    suspend fun deleteArea(area: AreaConhecimento)
}