package com.example.s8144281assignment2

import com.example.s8144281assignment2.api.LoginResponse
import com.example.s8144281assignment2.repository.AppRepository
import com.example.s8144281assignment2.viewmodel.LoginViewModel
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeApi = FakeApi()
    private val viewModel = LoginViewModel(AppRepository(fakeApi))

    @Test
    fun `successful login passes the keypass to onSuccess`() {
        fakeApi.loginResponse = Response.success(LoginResponse("art"))
        viewModel.username = "8144281"
        viewModel.password = "Angus"
        var receivedKeypass: String? = null

        viewModel.login { receivedKeypass = it }

        assertEquals("art", receivedKeypass)
        assertEquals("", viewModel.message)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun `wrong password shows an error and does not navigate`() {
        fakeApi.loginResponse = Response.error(401, "".toResponseBody(null))
        viewModel.username = "8144281"
        viewModel.password = "wrong"
        var navigated = false

        viewModel.login { navigated = true }

        assertFalse(navigated)
        assertEquals("Login failed (code 401)", viewModel.message)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun `no internet shows a network error`() {
        fakeApi.shouldThrow = true
        viewModel.username = "8144281"
        viewModel.password = "Angus"

        viewModel.login { }

        assertTrue(viewModel.message.contains("Network error"))
        assertFalse(viewModel.isLoading)
    }
}