package com.example.s8144281assignment2

import androidx.lifecycle.SavedStateHandle
import com.example.s8144281assignment2.api.DashboardResponse
import com.example.s8144281assignment2.repository.AppRepository
import com.example.s8144281assignment2.viewmodel.DashboardViewModel
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class DashboardViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeApi = FakeApi()

    // The ViewModel loads data when it is created, so build it inside each test
    private fun createViewModel() = DashboardViewModel(
        AppRepository(fakeApi),
        SavedStateHandle(mapOf("keypass" to "art"))
    )

    @Test
    fun `loads the list of artworks`() {
        fakeApi.dashboardResponse =
            Response.success(DashboardResponse(sampleEntities, sampleEntities.size))

        val viewModel = createViewModel()

        assertEquals(sampleEntities, viewModel.entities)
        assertFalse(viewModel.isLoading)
        assertEquals("", viewModel.error)
    }

    @Test
    fun `uses the keypass from the route`() {
        fakeApi.dashboardResponse =
            Response.success(DashboardResponse(sampleEntities, sampleEntities.size))

        createViewModel()

        assertEquals("art", fakeApi.lastKeypass)
    }

    @Test
    fun `failed request shows an error`() {
        fakeApi.dashboardResponse = Response.error(404, "".toResponseBody(null))

        val viewModel = createViewModel()

        assertTrue(viewModel.entities.isEmpty())
        assertEquals("Failed to load data (code 404)", viewModel.error)
        assertFalse(viewModel.isLoading)
    }
}