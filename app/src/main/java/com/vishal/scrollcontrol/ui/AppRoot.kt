package com.vishal.scrollcontrol.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.ui.res.stringResource
import com.vishal.scrollcontrol.R
import com.vishal.scrollcontrol.ui.screens.MainScreen
import com.vishal.scrollcontrol.ui.screens.SettingsScreen
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
                    label = { Text(stringResource(id = R.string.nav_main)) }
                )
                NavigationBarItem(
                    selected = index == 1,
                    onClick = { index = 1 },
                    icon = { Icon(Icons.Default.Timeline, contentDescription = null) },
                    label = { Text(stringResource(id = R.string.nav_stats)) }
                )
                NavigationBarItem(
                    selected = index == 2,
                    onClick = { index = 2 },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text(stringResource(id = R.string.nav_settings)) }
                )
            }
        }
    ) { _ ->
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            when (index) {
                0 -> MainScreen(serviceActive = true, onToggleService = {})
                1 -> StatsScreen()
                else -> SettingsScreen()
            }
        }
    }
}
