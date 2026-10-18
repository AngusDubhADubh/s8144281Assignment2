package com.example.s8144281assignment2

import com.example.s8144281assignment2.api.Entity
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
    var lastKeypass: String? = null

    override suspend fun login(request: LoginRequest): Response<LoginResponse> {
        if (shouldThrow) throw IOException("no internet")
        return loginResponse
    }

    override suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        lastKeypass = keypass
        if (shouldThrow) throw IOException("no internet")
        return dashboardResponse
    }

}

val sampleEntities = listOf(
    Entity("Mona Lisa", "Leonardo da Vinci", "Oil paint", 1503, "A portrait"),
    Entity("The Starry Night", "Vincent van Gogh", "Oil paint", 1889, "A night scene")
)