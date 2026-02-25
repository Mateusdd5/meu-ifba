package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.PreferenciaUsuarioDao
import com.ifba.meuifba.data.remote.api.UsuarioApi
import com.ifba.meuifba.domain.model.CategoriaModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenciaUsuarioRepository @Inject constructor(
    private val preferenciaDao: PreferenciaUsuarioDao,
    private val usuarioApi: UsuarioApi
) {

    fun getPreferencias(usuarioId: Long): Flow<Resource<List<CategoriaModel>>> = flow {
        emit(Resource.Loading())

        try {
            val response = usuarioApi.getPreferencias(usuarioId)

            if (response.isSuccessful && response.body() != null) {
                // TODO: Converter DTO -> Model
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro ao buscar preferências"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    suspend fun updatePreferencias(usuarioId: Long, categoriaIds: List<Long>): Resource<Unit> {
        return try {
            val response = usuarioApi.updatePreferencias(usuarioId, categoriaIds)

            if (response.isSuccessful) {
                // TODO: Atualizar localmente
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro ao atualizar preferências")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }
}