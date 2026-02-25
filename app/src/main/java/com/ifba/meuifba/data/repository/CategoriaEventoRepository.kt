package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.CategoriaEventoDao
import com.ifba.meuifba.data.remote.api.UsuarioApi
import com.ifba.meuifba.domain.model.CategoriaModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriaEventoRepository @Inject constructor(
    private val categoriaDao: CategoriaEventoDao,
    private val usuarioApi: UsuarioApi
) {

    fun getCategorias(): Flow<Resource<List<CategoriaModel>>> = flow {
        emit(Resource.Loading())

        try {
            val response = usuarioApi.getCategorias()

            if (response.isSuccessful && response.body() != null) {
                // TODO: Converter DTO -> Model
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro ao buscar categorias"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }
}