package com.example.s8144281assignment2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
fun DetailsScreen(keypass: String, index: Int) {
    var entity by remember { mutableStateOf<Entity?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitInstance.api.getDashboard(keypass)
            if (response.isSuccessful) {
                // take only the item that was tapped
                entity = response.body()?.entities?.getOrNull(index)
            } else {
                error = "Failed to load data (code ${response.code()})"
            }
        } catch (e: Exception) {
            error = "Network error: ${e.localizedMessage}"
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val item = entity

        if (isLoading) {
            CircularProgressIndicator()
        } else if (error.isNotEmpty()) {
            Text(error)
        } else if (item == null) {
            Text("Item not found")
        } else {
            Text(text = item.artworkTitle, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Artist: ${item.artist}")
            Text(text = "Medium: ${item.medium}")
            Text(text = "Year: ${item.year}")

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Description", style = MaterialTheme.typography.titleMedium)
            Text(text = item.description)
        }
    }
}