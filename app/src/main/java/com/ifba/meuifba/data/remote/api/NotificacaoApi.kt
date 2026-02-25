package com.ifba.meuifba.data.remote.api

import com.ifba.meuifba.data.remote.dto.NotificacaoResponse
import retrofit2.Response
import retrofit2.http.*

interface NotificacaoApi {

    // Listar notificações do usuário
    @GET("notificacoes/{usuarioId}")
    suspend fun getNotificacoes(
        @Path("usuarioId") usuarioId: Long
    ): Response<List<NotificacaoResponse>>

    // Listar notificações não lidas
    @GET("notificacoes/{usuarioId}/nao-lidas")
    suspend fun getNotificacoesNaoLidas(
        @Path("usuarioId") usuarioId: Long
    ): Response<List<NotificacaoResponse>>

    // Contar notificações não lidas
    @GET("notificacoes/{usuarioId}/count")
    suspend fun getCountNaoLidas(
        @Path("usuarioId") usuarioId: Long
    ): Response<Int>

    // Marcar notificação como lida
    @PUT("notificacoes/{id}/visualizar")
    suspend fun marcarComoLida(
        @Path("id") notificacaoId: Long
    ): Response<Unit>

    // Marcar todas como lidas
    @PUT("notificacoes/{usuarioId}/visualizar-todas")
    suspend fun marcarTodasComoLidas(
        @Path("usuarioId") usuarioId: Long
    ): Response<Unit>

    // Deletar notificação
    @DELETE("notificacoes/{id}")
    suspend fun deleteNotificacao(
        @Path("id") notificacaoId: Long
    ): Response<Unit>
}