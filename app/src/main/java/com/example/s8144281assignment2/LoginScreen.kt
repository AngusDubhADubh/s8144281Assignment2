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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.s8144281assignment2.api.LoginRequest
import com.example.s8144281assignment2.api.RetrofitInstance
import com.example.s8144281assignment2.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    // Values that Compose watches. When they change, the screen redraws.
//    var username by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var message by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }

    // Start a coroutine from the button click
    //val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = { Text("Username (student ID)") },
            singleLine = true
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Password (first name)") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading,
            onClick = { viewModel.login(onLoginSuccess) }
        ) {
            Text(if (viewModel.isLoading) "Logging in..." else "Login")
        }

        Spacer(Modifier.height(16.dp))
        Text(viewModel.message)
    }
}