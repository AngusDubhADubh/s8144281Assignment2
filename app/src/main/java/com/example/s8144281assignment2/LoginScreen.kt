package com.example.s8144281assignment2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.s8144281assignment2.api.LoginRequest
import com.example.s8144281assignment2.api.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    // Values that Compose watches. When they change, the screen redraws.
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Start a coroutine from the button click
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { Text("Username (student ID)") },
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password (first name)") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            onClick = {
                isLoading = true
                message = ""
                scope.launch {
                    try {

                        // Send username + password to the API
                        val response = RetrofitInstance.api.login(
                            LoginRequest(username.trim(), password)
                        )
                        if (response.isSuccessful) {
                            val keypass = response.body()?.keypass      // 1. save it in a variable
                            if (keypass != null) {
                                onLoginSuccess(keypass)                 // 2. tell the app to move on
                            } else {
                                message = "Login failed: no keypass returned"   // 3. otherwise show an error
                            }
                        } else {
                            message = "Login failed (code ${response.code()})"
                        }
                    } catch (e: Exception) {
                        // No internet, timeout, etc.
                        message = "Network error: ${e.localizedMessage}"
                    }
                    isLoading = false
                }
            }
        ) {
            Text(if (isLoading) "Logging in..." else "Login")
        }

        Spacer(Modifier.height(16.dp))
        Text(message)
    }
}