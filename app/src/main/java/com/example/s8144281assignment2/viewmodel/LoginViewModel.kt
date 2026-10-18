package com.example.s8144281assignment2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8144281assignment2.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var message by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun login(onSuccess: (String) -> Unit) {
        isLoading = true
        message = ""
        viewModelScope.launch {
            repository.login(username.trim(), password).fold(
                onSuccess = { keypass -> onSuccess(keypass) },
                onFailure = { error -> message = error.message ?: "Login failed" }
            )
            isLoading = false
        }
    }
}