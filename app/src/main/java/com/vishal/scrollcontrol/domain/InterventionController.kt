package com.vishal.scrollcontrol.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class Stage { GRACE, NUDGE, PAUSE, REDIRECT, BLOCK }

data class AppState(
    val packageName: String,
    val stage: Stage,
    val lastTransitionAt: Long,
    val cooldownUntil: Long
)

class InterventionController {
    private val _state = MutableStateFlow<Map<String, AppState>>(emptyMap())
    val state: StateFlow<Map<String, AppState>> = _state

    fun update(packageName: String, now: Long, graceMs: Long, cooldownMs: Long): AppState {
        val current = _state.value[packageName]
        val next = when {
            current == null -> AppState(packageName, Stage.GRACE, now, 0L)
            current.stage == Stage.GRACE && now - current.lastTransitionAt >= graceMs -> current.copy(stage = Stage.NUDGE, lastTransitionAt = now)
            current.stage == Stage.NUDGE && now - current.lastTransitionAt >= 30_000L -> current.copy(stage = Stage.PAUSE, lastTransitionAt = now)
            current.stage == Stage.PAUSE && now - current.lastTransitionAt >= 60_000L -> current.copy(stage = Stage.REDIRECT, lastTransitionAt = now)
            current.stage == Stage.REDIRECT && now - current.lastTransitionAt >= 60_000L -> current.copy(stage = Stage.BLOCK, lastTransitionAt = now, cooldownUntil = now + cooldownMs)
            current.stage == Stage.BLOCK && now >= current.cooldownUntil -> AppState(packageName, Stage.GRACE, now, 0L)
            else -> current
        }
        _state.value = _state.value.toMutableMap().apply { put(packageName, next) }
        return next
    }
}
