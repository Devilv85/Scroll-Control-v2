package com.vishal.scrollcontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishal.scrollcontrol.core.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import com.vishal.scrollcontrol.core.Keys
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore by preferencesDataStore(name = "scroll_control_settings")

data class SettingsState(
    val enableYouTube: Boolean = true,
    val enableInstagram: Boolean = true,
    val overlaysEnabled: Boolean = true,
    val graceMinutes: Int = 5,
    val cooldownMinutes: Int = 60,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository,
    private val appContext: android.app.Application
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state

    init {
        viewModelScope.launch {
            repo.setDefaultsIfMissing()
            val s = repo.getSnapshot()
            _state.value = _state.value.copy(
                enableYouTube = s.enableYouTube,
                enableInstagram = s.enableInstagram,
                overlaysEnabled = s.overlaysEnabled,
                graceMinutes = s.graceMinutes,
                cooldownMinutes = s.cooldownMinutes,
            )
        }
    }

    fun toggleYouTube(v: Boolean) = update { it.copy(enableYouTube = v) { p -> p[Keys.enableYouTube] = v } }
    fun toggleInstagram(v: Boolean) = update { it.copy(enableInstagram = v) { p -> p[Keys.enableInstagram] = v } }
    fun toggleOverlays(v: Boolean) = update { it.copy(overlaysEnabled = v) { p -> p[Keys.overlaysEnabled] = v } }
    fun setGrace(v: Int) = update { it.copy(graceMinutes = v) { p -> p[Keys.graceMinutes] = v } }
    fun setCooldown(v: Int) = update { it.copy(cooldownMinutes = v) { p -> p[Keys.cooldownMinutes] = v } }
    fun resetDefaults() {
        viewModelScope.launch {
            repo.setDefaultsIfMissing()
            val s = repo.getSnapshot()
            _state.value = SettingsState(
                enableYouTube = s.enableYouTube,
                enableInstagram = s.enableInstagram,
                overlaysEnabled = s.overlaysEnabled,
                graceMinutes = s.graceMinutes,
                cooldownMinutes = s.cooldownMinutes,
            )
        }
    }

    private fun update(block: (SettingsState) -> SettingsStateWithPersist) {
        viewModelScope.launch {
            val next = block(_state.value)
            appContext.dataStore.edit { prefs: Preferences -> next.persist(prefs) }
            _state.value = next.state
        }
    }
}

private data class SettingsStateWithPersist(
    val state: SettingsState,
    val persist: (Preferences) -> Unit
)

private fun SettingsState.copy(
    enableYouTube: Boolean = this.enableYouTube,
    enableInstagram: Boolean = this.enableInstagram,
    overlaysEnabled: Boolean = this.overlaysEnabled,
    graceMinutes: Int = this.graceMinutes,
    cooldownMinutes: Int = this.cooldownMinutes,
    persist: (Preferences) -> Unit,
) = SettingsStateWithPersist(
    state = SettingsState(enableYouTube, enableInstagram, overlaysEnabled, graceMinutes, cooldownMinutes),
    persist = persist
)
