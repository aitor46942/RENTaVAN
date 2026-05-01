package com.example.rentavan.data.network

import com.example.rentavan.data.model.auth.AuthResponse
import com.example.rentavan.data.model.auth.LoginRequest
import com.example.rentavan.data.model.auth.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RentavanApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
