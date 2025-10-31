package com.vishal.scrollcontrol.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.vishal.scrollcontrol.ui.screens.MainScreen
import com.vishal.scrollcontrol.ui.screens.StatsScreen

@Composable
fun AppRoot() {
    var index by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = index == 0,
                    onClick = { index = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Main") }
                )
                NavigationBarItem(
                    selected = index == 1,
                    onClick = { index = 1 },
                    icon = { Icon(Icons.Default.Timeline, contentDescription = null) },
                    label = { Text("Stats") }
                )
                NavigationBarItem(
                    selected = index == 2,
                    onClick = { index = 2 },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") }
                )
            }
        }
    ) { padding ->
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            when (index) {
                0 -> MainScreen(serviceActive = true, onToggleService = {})
                1 -> StatsScreen()
                else -> Text("Settings (coming soon)")
            }
        }
    }
}
