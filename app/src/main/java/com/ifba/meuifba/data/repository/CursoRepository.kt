package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.CursoDao
import com.ifba.meuifba.data.remote.api.UsuarioApi
import com.ifba.meuifba.domain.model.CursoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CursoRepository @Inject constructor(
    private val cursoDao: CursoDao,
    private val usuarioApi: UsuarioApi
) {

    fun getCursos(): Flow<Resource<List<CursoModel>>> = flow {
        emit(Resource.Loading())

        try {
            val response = usuarioApi.getCursos()

            if (response.isSuccessful && response.body() != null) {
                // TODO: Converter DTO -> Model
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro ao buscar cursos"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }
}