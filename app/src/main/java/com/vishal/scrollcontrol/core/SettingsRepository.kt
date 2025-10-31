package com.vishal.scrollcontrol.core

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "scroll_control_settings")

object Keys {
    val enableYouTube = booleanPreferencesKey("enable_youtube")
    val enableInstagram = booleanPreferencesKey("enable_instagram")
    val graceMinutes = intPreferencesKey("grace_minutes")
    val cooldownMinutes = intPreferencesKey("cooldown_minutes")
    val overlaysEnabled = booleanPreferencesKey("overlays_enabled")
}

class SettingsRepository(private val context: Context) {
    val enableYouTubeFlow: Flow<Boolean> = context.dataStore.data.map { it[Keys.enableYouTube] ?: true }
    val enableInstagramFlow: Flow<Boolean> = context.dataStore.data.map { it[Keys.enableInstagram] ?: true }
    val graceMinutesFlow: Flow<Int> = context.dataStore.data.map { it[Keys.graceMinutes] ?: 5 }
    val cooldownMinutesFlow: Flow<Int> = context.dataStore.data.map { it[Keys.cooldownMinutes] ?: 60 }
    val overlaysEnabledFlow: Flow<Boolean> = context.dataStore.data.map { it[Keys.overlaysEnabled] ?: true }

    suspend fun getSnapshot(): Snapshot = Snapshot(
        enableYouTube = enableYouTubeFlow.first(),
        enableInstagram = enableInstagramFlow.first(),
        graceMinutes = graceMinutesFlow.first(),
        cooldownMinutes = cooldownMinutesFlow.first(),
        overlaysEnabled = overlaysEnabledFlow.first(),
    )

    suspend fun setDefaultsIfMissing() {
        context.dataStore.edit { prefs: Preferences ->
            if (!prefs.contains(Keys.enableYouTube)) prefs[Keys.enableYouTube] = true
            if (!prefs.contains(Keys.enableInstagram)) prefs[Keys.enableInstagram] = true
            if (!prefs.contains(Keys.graceMinutes)) prefs[Keys.graceMinutes] = 5
            if (!prefs.contains(Keys.cooldownMinutes)) prefs[Keys.cooldownMinutes] = 60
            if (!prefs.contains(Keys.overlaysEnabled)) prefs[Keys.overlaysEnabled] = true
        }
    }

    data class Snapshot(
        val enableYouTube: Boolean,
        val enableInstagram: Boolean,
        val graceMinutes: Int,
        val cooldownMinutes: Int,
        val overlaysEnabled: Boolean,
    )
}
