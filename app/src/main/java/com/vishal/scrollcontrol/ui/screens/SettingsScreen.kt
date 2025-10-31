package com.vishal.scrollcontrol.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vishal.scrollcontrol.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(vm: SettingsViewModel = hiltViewModel()) {
    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("YouTube")
        Switch(checked = state.enableYouTube, onCheckedChange = vm::toggleYouTube)
        Spacer(Modifier.height(12.dp))
        Text("Instagram")
        Switch(checked = state.enableInstagram, onCheckedChange = vm::toggleInstagram)
        Spacer(Modifier.height(12.dp))
        Text("Overlays")
        Switch(checked = state.overlaysEnabled, onCheckedChange = vm::toggleOverlays)
        Spacer(Modifier.height(12.dp))
        Text("Grace: ${state.graceMinutes} min")
        Slider(value = state.graceMinutes.toFloat(), onValueChange = { vm.setGrace(it.toInt()) }, valueRange = 1f..10f, steps = 8)
        Spacer(Modifier.height(12.dp))
        Text("Cooldown: ${state.cooldownMinutes} min")
        Slider(value = state.cooldownMinutes.toFloat(), onValueChange = { vm.setCooldown(it.toInt()) }, valueRange = 15f..120f, steps = 20)
        Spacer(Modifier.height(12.dp))
        Button(onClick = vm::resetDefaults) { Text("Reset defaults") }
    }
}
