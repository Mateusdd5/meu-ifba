package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.NotificacaoDao
import com.ifba.meuifba.data.remote.api.NotificacaoApi
import com.ifba.meuifba.domain.model.NotificacaoModel
import com.ifba.meuifba.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificacaoRepository @Inject constructor(
    private val notificacaoDao: NotificacaoDao,
    private val notificacaoApi: NotificacaoApi
) {

    // Buscar notificações
    fun getNotificacoes(usuarioId: Long): Flow<Resource<List<NotificacaoModel>>> = flow {
        emit(Resource.Loading())

        try {
            val response = notificacaoApi.getNotificacoes(usuarioId)

            if (response.isSuccessful && response.body() != null) {
                // TODO: Salvar localmente
                // TODO: Converter DTO -> Model
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro ao buscar notificações"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    // Buscar notificações não lidas
    fun getNotificacoesNaoLidas(usuarioId: Long): Flow<Resource<List<NotificacaoModel>>> = flow {
        emit(Resource.Loading())

        try {
            // Buscar localmente
            val localNaoLidas = notificacaoDao.getNotificacoesNaoLidas(usuarioId)

            // TODO: Converter Entity -> Model e emitir

            // Buscar da API
            val response = notificacaoApi.getNotificacoesNaoLidas(usuarioId)

            if (response.isSuccessful && response.body() != null) {
                // TODO: Converter DTO -> Model
                emit(Resource.Success(emptyList()))
            } else {
                emit(Resource.Error("Erro ao buscar notificações"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Erro desconhecido"))
        }
    }

    // Contar não lidas
    fun getCountNaoLidas(usuarioId: Long): Flow<Int> {
        return notificacaoDao.countNotificacoesNaoLidas(usuarioId)
    }

    // Marcar como lida
    suspend fun marcarComoLida(notificacaoId: Long): Resource<Unit> {
        return try {
            val response = notificacaoApi.marcarComoLida(notificacaoId)

            if (response.isSuccessful) {
                // Atualizar localmente
                notificacaoDao.marcarComoLida(notificacaoId)
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro ao marcar notificação")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

    // Marcar todas como lidas
    suspend fun marcarTodasComoLidas(usuarioId: Long): Resource<Unit> {
        return try {
            val response = notificacaoApi.marcarTodasComoLidas(usuarioId)

            if (response.isSuccessful) {
                // Atualizar localmente
                notificacaoDao.marcarTodasComoLidas(usuarioId)
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro ao marcar notificações")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }
}