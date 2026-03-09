package com.ifba.meuifba.data.remote.api

import com.ifba.meuifba.data.remote.dto.DashboardResponse
import retrofit2.Response
import retrofit2.http.GET

interface AdminApi {

    @GET("admin/dashboard")
    suspend fun getDashboard(): Response<DashboardResponse>
}