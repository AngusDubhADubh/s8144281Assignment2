package com.example.s8144281assignment2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.s8144281assignment2.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(viewModel: DetailsViewModel = hiltViewModel()) {
    // Same blue gradient background as the login screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DeepBlue, MidBlue)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            val item = viewModel.entity

            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Yellow
                )
            } else if (viewModel.error.isNotEmpty()) {
                Text(text = viewModel.error, color = Color.White)
            } else if (item == null) {
                Text(text = "Item not found", color = Color.White)
            } else {
                // Title, with the year in a yellow pill underneath
                Text(
                    text = item.artworkTitle,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                Surface(shape = RoundedCornerShape(50), color = Yellow) {
                    Text(
                        text = item.year.toString(),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                        color = DeepBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.height(24.dp))

                // Card with artist and medium
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        DetailRow(label = "Artist", value = item.artist)
                        Spacer(Modifier.height(16.dp))
                        DetailRow(label = "Medium", value = item.medium)
                    }
                }
                Spacer(Modifier.height(16.dp))

                // Card with the description
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Description",
                            color = DeepBlue,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = item.description,
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }
    }
}

// One label-and-value pair, used for artist and medium
@Composable
private fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = MidBlue,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(text = value, color = Color.Black, fontSize = 18.sp)
    }
}