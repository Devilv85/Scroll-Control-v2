package com.vishal.scrollcontrol.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.vishal.scrollcontrol.ui.viewmodel.StatsViewModel

@Composable
fun StatsScreen(vm: StatsViewModel = hiltViewModel()) {
    val today by vm.todayCount.collectAsState(initial = 0)
    val streak by vm.streak.collectAsState(initial = 0)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Today: $today", style = MaterialTheme.typography.titleLarge)
        Text(text = "Streak: $streak", style = MaterialTheme.typography.bodyLarge)
    }
}
