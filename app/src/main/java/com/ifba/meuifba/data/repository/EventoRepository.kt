package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.EventoDao
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

    // Buscar todos os eventos
    fun getEventos(): Flow<Resource<List<EventoModel>>> = flow {
        emit(Resource.Loading())

        try {
            // Primeiro emite dados locais (cache)
            val localEventos = eventoDao.getAllEventos()
            // TODO: Converter Entity -> Model (faremos com Mappers depois)

            // Depois busca da API
            val response = eventoApi.getEventos()

            if (response.isSuccessful && response.body() != null) {
                val eventosResponse = response.body()!!

                // Salvar no banco local (cache)
                // TODO: Converter DTO -> Entity e salvar

                // Converter para Model
                // TODO: Converter DTO -> Model

                // Por enquanto, mock
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro ao buscar eventos: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    // Buscar evento por ID
    suspend fun getEventoById(eventoId: Long): EventoModel? {
        return try {
            // Tentar buscar localmente primeiro
            val localEvento = eventoDao.getEventoById(eventoId)

            // Se não encontrar, buscar da API
            if (localEvento == null) {
                val response = eventoApi.getEventoById(eventoId)
                if (response.isSuccessful && response.body() != null) {
                    // TODO: Converter DTO -> Entity -> Model
                    null // temporário
                } else {
                    null
                }
            } else {
                // TODO: Converter Entity -> Model
                null // temporário
            }
        } catch (e: Exception) {
            null
        }
    }

    // Buscar por categoria
    fun getEventosByCategoria(categoriaId: Long): Flow<Resource<List<EventoModel>>> = flow {
        emit(Resource.Loading())

        try {
            val response = eventoApi.getEventosByCategoria(categoriaId)

            if (response.isSuccessful && response.body() != null) {
                // TODO: Converter DTO -> Model
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro ao buscar eventos"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    // Buscar (search)
    fun searchEventos(query: String): Flow<Resource<List<EventoModel>>> = flow {
        emit(Resource.Loading())

        try {
            // Buscar localmente primeiro
            val localResults = eventoDao.searchEventos("%$query%")

            // TODO: Converter Entity -> Model e emitir

            // Depois buscar da API
            val response = eventoApi.searchEventos(query)

            if (response.isSuccessful && response.body() != null) {
                // TODO: Converter DTO -> Model
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro na busca"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    // Criar evento
    suspend fun createEvento(request: CreateEventoRequest): Resource<EventoModel?> {
        return try {
            val response = eventoApi.createEvento(request)

            if (response.isSuccessful && response.body() != null) {
                // TODO: Converter DTO -> Entity -> salvar localmente
                // TODO: Converter DTO -> Model
                Resource.Success(null)
            } else {
                Resource.Error("Erro ao criar evento: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

    // Atualizar evento
    suspend fun updateEvento(
        eventoId: Long,
        request: UpdateEventoRequest
    ): Resource<EventoModel?> {
        return try {
            val response = eventoApi.updateEvento(eventoId, request)

            if (response.isSuccessful && response.body() != null) {
                // TODO: Atualizar no banco local
                Resource.Success(null)
            } else {
                Resource.Error("Erro ao atualizar evento")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

    // Deletar evento
// Deletar evento
    suspend fun deleteEvento(eventoId: Long): Resource<Unit> {
        return try {
            val response = eventoApi.deleteEvento(eventoId)

            if (response.isSuccessful) {
                // Deletar localmente também
                val evento = eventoDao.getEventoById(eventoId)
                if (evento != null) {
                    eventoDao.deleteEvento(evento)
                }
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro ao deletar evento")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }
}