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
class DashboardViewModel @Inject constructor(
    private val repository: AppRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // "keypass" is the {keypass} part of the route "dashboard/{keypass}"
    private val keypass: String = savedStateHandle["keypass"] ?: ""

    var entities by mutableStateOf<List<Entity>>(emptyList())
    var isLoading by mutableStateOf(true)
    var error by mutableStateOf("")

    // Runs as soon as the ViewModel is created
    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getEntities(keypass).fold(
                onSuccess = { entities = it },
                onFailure = { error = it.message ?: "Failed to load data" }
            )
            isLoading = false
        }
    }
}