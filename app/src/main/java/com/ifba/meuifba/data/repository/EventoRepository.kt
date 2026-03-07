package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.EventoDao
import com.ifba.meuifba.data.mapper.toModel
import com.ifba.meuifba.data.remote.api.EventoApi
import com.ifba.meuifba.data.remote.dto.CreateEventoRequest
import com.ifba.meuifba.data.remote.dto.UpdateEventoRequest
import com.ifba.meuifba.domain.model.EventoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventoRepository @Inject constructor(
    private val eventoDao: EventoDao,
    private val eventoApi: EventoApi
) {

    fun getEventos(): Flow<Resource<List<EventoModel>>> = flow {
        emit(Resource.Loading())
        try {
            val response = eventoApi.getEventos()
            if (response.isSuccessful && response.body() != null) {
                val eventos = response.body()!!.map { it.toModel() }
                emit(Resource.Success(eventos))
            } else {
                emit(Resource.Error("Erro ao buscar eventos: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    suspend fun getEventoById(eventoId: Long): EventoModel? {
        return try {
            val response = eventoApi.getEventoById(eventoId)
            if (response.isSuccessful && response.body() != null) {
                response.body()!!.toModel()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getEventosByCategoria(categoriaId: Long): Flow<Resource<List<EventoModel>>> = flow {
        emit(Resource.Loading())
        try {
            val response = eventoApi.getEventosByCategoria(categoriaId)
            if (response.isSuccessful && response.body() != null) {
                val eventos = response.body()!!.map { it.toModel() }
                emit(Resource.Success(eventos))
            } else {
                emit(Resource.Error("Erro ao buscar eventos"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    fun searchEventos(query: String): Flow<Resource<List<EventoModel>>> = flow {
        emit(Resource.Loading())
        try {
            val response = eventoApi.searchEventos(query)
            if (response.isSuccessful && response.body() != null) {
                val eventos = response.body()!!.map { it.toModel() }
                emit(Resource.Success(eventos))
            } else {
                emit(Resource.Error("Erro na busca"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    suspend fun createEvento(request: CreateEventoRequest): Resource<EventoModel?> {
        return try {
            val response = eventoApi.createEvento(request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toModel())
            } else {
                Resource.Error("Erro ao criar evento: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

    suspend fun updateEvento(eventoId: Long, request: UpdateEventoRequest): Resource<EventoModel?> {
        return try {
            val response = eventoApi.updateEvento(eventoId, request)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toModel())
            } else {
                Resource.Error("Erro ao atualizar evento")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

    suspend fun deleteEvento(eventoId: Long): Resource<Unit> {
        return try {
            val response = eventoApi.deleteEvento(eventoId)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro ao deletar evento")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }
}