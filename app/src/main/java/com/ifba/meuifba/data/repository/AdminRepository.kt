package com.ifba.meuifba.data.repository

import com.ifba.meuifba.data.remote.api.AdminApi
import com.ifba.meuifba.data.remote.dto.DashboardResponse
import com.ifba.meuifba.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepository @Inject constructor(
    private val adminApi: AdminApi
) {
    suspend fun getDashboard(): Resource<DashboardResponse> {
        return try {
            val response = adminApi.getDashboard()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Erro ao buscar dashboard: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Erro desconhecido")
        }
    }
}