package com.example.s8144281assignment2

import androidx.lifecycle.SavedStateHandle
import com.example.s8144281assignment2.api.DashboardResponse
import com.example.s8144281assignment2.repository.AppRepository
import com.example.s8144281assignment2.viewmodel.DetailsViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeApi = FakeApi().apply {
        dashboardResponse = Response.success(DashboardResponse(sampleEntities, sampleEntities.size))
    }

    private fun createViewModel(index: String) = DetailsViewModel(
        AppRepository(fakeApi),
        SavedStateHandle(mapOf("keypass" to "art", "index" to index))
    )

    @Test
    fun `shows the artwork at the tapped position`() {
        val viewModel = createViewModel("1")

        assertEquals(sampleEntities[1], viewModel.entity)
        assertFalse(viewModel.isLoading)
    }

    @Test
    fun `index out of range gives no artwork`() {
        val viewModel = createViewModel("99")

        assertNull(viewModel.entity)
        assertFalse(viewModel.isLoading)
    }
}