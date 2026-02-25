package com.ifba.meuifba.data.remote.api

import com.ifba.meuifba.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface EventoApi {

    // Listar todos os eventos
    @GET("eventos")
    suspend fun getEventos(): Response<List<EventoResponse>>

    // Buscar evento por ID
    @GET("eventos/{id}")
    suspend fun getEventoById(
        @Path("id") eventoId: Long
    ): Response<EventoResponse>

    // Buscar eventos por categoria
    @GET("eventos/categoria/{categoriaId}")
    suspend fun getEventosByCategoria(
        @Path("categoriaId") categoriaId: Long
    ): Response<List<EventoResponse>>

    // Buscar eventos (search)
    @GET("eventos/search")
    suspend fun searchEventos(
        @Query("q") query: String
    ): Response<List<EventoResponse>>

    // Criar evento
    @POST("eventos")
    suspend fun createEvento(
        @Body request: CreateEventoRequest
    ): Response<EventoResponse>

    // Atualizar evento
    @PUT("eventos/{id}")
    suspend fun updateEvento(
        @Path("id") eventoId: Long,
        @Body request: UpdateEventoRequest
    ): Response<EventoResponse>

    // Deletar evento
    @DELETE("eventos/{id}")
    suspend fun deleteEvento(
        @Path("id") eventoId: Long
    ): Response<Unit>

    // Marcar participação
    @POST("eventos/marcar")
    suspend fun marcarParticipacao(
        @Body request: MarcarParticipacaoRequest
    ): Response<MarcacaoParticipacaoResponse>

    // Desmarcar participação
    @DELETE("eventos/marcar/{usuarioId}/{eventoId}")
    suspend fun desmarcarParticipacao(
        @Path("usuarioId") usuarioId: Long,
        @Path("eventoId") eventoId: Long
    ): Response<Unit>

    // Listar eventos marcados pelo usuário
    @GET("eventos/marcados/{usuarioId}")
    suspend fun getEventosMarcados(
        @Path("usuarioId") usuarioId: Long
    ): Response<List<EventoResponse>>

    // Buscar estatísticas do evento
    @GET("eventos/{id}/estatisticas")
    suspend fun getEstatisticas(
        @Path("id") eventoId: Long
    ): Response<EstatisticaEventoResponse>

    // Buscar mídias do evento
    @GET("eventos/{id}/midias")
    suspend fun getMidias(
        @Path("id") eventoId: Long
    ): Response<List<MidiaEventoResponse>>
}