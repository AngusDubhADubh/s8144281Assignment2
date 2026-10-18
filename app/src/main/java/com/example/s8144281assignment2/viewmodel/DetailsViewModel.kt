package com.example.s8144281assignment2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8144281assignment2.api.Entity
import com.example.s8144281assignment2.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Both come from the route "details/{keypass}/{index}"
    private val keypass: String = savedStateHandle["keypass"] ?: ""
    private val index: Int = savedStateHandle.get<String>("index")?.toIntOrNull() ?: 0

    var entity by mutableStateOf<Entity?>(null)
    var isLoading by mutableStateOf(true)
    var error by mutableStateOf("")

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getEntities(keypass).fold(
                onSuccess = { entity = it.getOrNull(index) },
                onFailure = { error = it.message ?: "Failed to load data" }
            )
            isLoading = false
        }
    }
}