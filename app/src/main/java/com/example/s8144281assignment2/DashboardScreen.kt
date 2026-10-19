package com.example.s8144281assignment2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.s8144281assignment2.api.Entity
import com.example.s8144281assignment2.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    onEntityClick: (Int) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    // Same blue gradient background as the other screens
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(DeepBlue, MidBlue)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Header
            Text(
                text = "Artworks",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tap an artwork for details",
                color = Yellow,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(16.dp))

            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Yellow
                )
            } else if (viewModel.error.isNotEmpty()) {
                Text(text = viewModel.error, color = Color.White)
            } else {
                // LazyColumn instead of RecyclerView
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(viewModel.entities) { index, entity ->
                        EntityCard(entity, onClick = { onEntityClick(index) })
                    }
                }
            }
        }
    }
}

// One row in the list: a summary of the artwork (no description)
@Composable
private fun EntityCard(entity: Entity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entity.artworkTitle,
                    color = DeepBlue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = entity.artist, color = Color.DarkGray, fontSize = 15.sp)
                Text(text = entity.medium, color = Color.Gray, fontSize = 13.sp)
            }
            Spacer(Modifier.padding(horizontal = 6.dp))
            // Year in a yellow pill, matching the Details screen
            Surface(shape = RoundedCornerShape(50), color = Yellow) {
                Text(
                    text = entity.year.toString(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}