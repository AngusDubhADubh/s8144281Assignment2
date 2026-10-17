package com.example.s8144281assignment2.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("footscray/auth")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}