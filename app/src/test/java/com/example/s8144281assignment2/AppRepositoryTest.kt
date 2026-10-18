package com.example.s8144281assignment2

import com.example.s8144281assignment2.api.DashboardResponse
import com.example.s8144281assignment2.api.Entity
import com.example.s8144281assignment2.api.LoginResponse
import com.example.s8144281assignment2.repository.AppRepository
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class AppRepositoryTest {

    private val fakeApi = FakeApi()
    private val repository = AppRepository(fakeApi)

    @Test
    fun `login success returns the keypass`() = runTest {
        fakeApi.loginResponse = Response.success(LoginResponse("art"))

        val result = repository.login("8144281", "Angus")

        assertTrue(result.isSuccess)
        assertEquals("art", result.getOrNull())
    }

    @Test
    fun `login with wrong password fails`() = runTest {
        fakeApi.loginResponse = Response.error(401, "".toResponseBody(null))

        val result = repository.login("8144281", "wrong")

        assertTrue(result.isFailure)
        assertEquals("Login failed (code 401)", result.exceptionOrNull()?.message)
    }

    @Test
    fun `login with no internet fails with a network error`() = runTest {
        fakeApi.shouldThrow = true

        val result = repository.login("8144281", "Angus")

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message?.contains("Network error") == true)
    }

    @Test
    fun `getEntities success returns the list`() = runTest {
        val artwork = Entity("Mona Lisa", "Leonardo da Vinci", "Oil paint", 1503, "A portrait")
        fakeApi.dashboardResponse = Response.success(DashboardResponse(listOf(artwork), 1))

        val result = repository.getEntities("art")

        assertTrue(result.isSuccess)
        assertEquals(listOf(artwork), result.getOrNull())
    }

    @Test
    fun `getEntities with a bad keypass fails`() = runTest {
        fakeApi.dashboardResponse = Response.error(404, "".toResponseBody(null))

        val result = repository.getEntities("nope")

        assertTrue(result.isFailure)
        assertEquals("Failed to load data (code 404)", result.exceptionOrNull()?.message)
    }
}