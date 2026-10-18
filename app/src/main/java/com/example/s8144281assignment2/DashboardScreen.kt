package com.example.s8144281assignment2.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.s8144281assignment2.api.Entity
import com.example.s8144281assignment2.api.RetrofitInstance

@Composable
fun DashboardScreen(keypass: String) {
    var entities by remember { mutableStateOf<List<Entity>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    // Runs once when this screen first appears
    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getDashboard(keypass)
            if (response.isSuccessful) {
                entities = response.body()?.entities ?: emptyList()
            } else {
                error = "Failed to load data (code ${response.code()})"
            }
        } catch (e: Exception) {
            error = "Network error: ${e.localizedMessage}"
        }
        isLoading = false
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error.isNotEmpty()) {
            Text(error)
        } else {
            Text("Loaded ${entities.size} artworks")
        }
    }
}