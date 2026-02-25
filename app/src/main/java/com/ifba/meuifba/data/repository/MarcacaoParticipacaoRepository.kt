package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.local.database.dao.MarcacaoParticipacaoDao
import com.ifba.meuifba.data.remote.api.EventoApi
import com.ifba.meuifba.data.remote.dto.MarcarParticipacaoRequest
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarcacaoParticipacaoRepository @Inject constructor(
    private val marcacaoDao: MarcacaoParticipacaoDao,
    private val eventoApi: EventoApi
) {

    // Marcar participação
    suspend fun marcarParticipacao(usuarioId: Long, eventoId: Long): Resource<Unit> {
        return try {
            val request = MarcarParticipacaoRequest(usuarioId, eventoId)
            val response = eventoApi.marcarParticipacao(request)

            if (response.isSuccessful) {
                // Salvar localmente
                // TODO: Criar Entity e inserir
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro ao marcar participação")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

    // Desmarcar participação
    suspend fun desmarcarParticipacao(usuarioId: Long, eventoId: Long): Resource<Unit> {
        return try {
            val response = eventoApi.desmarcarParticipacao(usuarioId, eventoId)

            if (response.isSuccessful) {
                // Deletar localmente
                val marcacao = marcacaoDao.getMarcacao(usuarioId, eventoId)
                if (marcacao != null) {
                    marcacaoDao.deleteMarcacao(marcacao)
                }
                Resource.Success(Unit)
            } else {
                Resource.Error("Erro ao desmarcar participação")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }

    // Verificar se está marcado
    suspend fun isMarcado(usuarioId: Long, eventoId: Long): Boolean {
        return try {
            val marcacao = marcacaoDao.getMarcacao(usuarioId, eventoId)
            marcacao != null
        } catch (e: Exception) {
            false
        }
    }
}