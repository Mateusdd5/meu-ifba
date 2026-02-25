package com.ifba.meuifba.data.remote.api

import com.ifba.meuifba.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface UsuarioApi {

    // Login
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    // Cadastro
    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<UsuarioResponse>

    // Buscar usuário por ID
    @GET("usuarios/{id}")
    suspend fun getUsuarioById(
        @Path("id") usuarioId: Long
    ): Response<UsuarioResponse>

    // Atualizar perfil
    @PUT("usuarios/{id}")
    suspend fun updateUsuario(
        @Path("id") usuarioId: Long,
        @Body usuario: UsuarioResponse
    ): Response<UsuarioResponse>

    // Atualizar senha
    @PUT("usuarios/{id}/senha")
    suspend fun updateSenha(
        @Path("id") usuarioId: Long,
        @Body senhaAtual: String,
        @Body novaSenha: String
    ): Response<Unit>

    // Buscar preferências do usuário
    @GET("usuarios/{id}/preferencias")
    suspend fun getPreferencias(
        @Path("id") usuarioId: Long
    ): Response<List<PreferenciaUsuarioResponse>>

    // Atualizar preferências
    @POST("usuarios/{id}/preferencias")
    suspend fun updatePreferencias(
        @Path("id") usuarioId: Long,
        @Body categoriaIds: List<Long>
    ): Response<List<PreferenciaUsuarioResponse>>

    // Listar todos os cursos disponíveis
    @GET("cursos")
    suspend fun getCursos(): Response<List<CursoResponse>>

    // Listar categorias de eventos
    @GET("categorias")
    suspend fun getCategorias(): Response<List<CategoriaEventoResponse>>
}