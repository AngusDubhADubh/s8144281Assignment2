package com.example.s8144281assignment2

import com.example.s8144281assignment2.api.ApiInterface
import com.example.s8144281assignment2.api.DashboardResponse
import com.example.s8144281assignment2.api.LoginRequest
import com.example.s8144281assignment2.api.LoginResponse
import java.io.IOException
import retrofit2.Response

class FakeApi : ApiInterface {

    // Tests change these to control what the "server" answers
    var loginResponse: Response<LoginResponse> = Response.success(LoginResponse("art"))
    var dashboardResponse: Response<DashboardResponse> =
        Response.success(DashboardResponse(emptyList(), 0))
    var shouldThrow = false

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        if (shouldThrow) throw IOException("no internet")
        return loginResponse
    }

    override suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        if (shouldThrow) throw IOException("no internet")
        return dashboardResponse
    }
}