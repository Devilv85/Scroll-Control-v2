package com.vishal.scrollcontrol.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.statsStore by preferencesDataStore(name = "scroll_control_stats")

object StatKeys {
    val lastDay = intPreferencesKey("last_day")
    val interventionsToday = intPreferencesKey("interventions_today")
    val streak = intPreferencesKey("streak")
}

class StatsRepository(private val context: Context) {
    val todayCount: Flow<Int> = context.statsStore.data.map { it[StatKeys.interventionsToday] ?: 0 }

    suspend fun markIntervention(dayOfYear: Int) {
        context.statsStore.edit { prefs: Preferences ->
            val prevDay = prefs[StatKeys.lastDay] ?: dayOfYear
            if (prevDay != dayOfYear) {
                // reset daily; update streak if consecutive
                val prevStreak = prefs[StatKeys.streak] ?: 0
                prefs[StatKeys.streak] = if (prevDay == dayOfYear - 1) prevStreak + 1 else 1
                prefs[StatKeys.interventionsToday] = 0
            }
            prefs[StatKeys.interventionsToday] = (prefs[StatKeys.interventionsToday] ?: 0) + 1
            prefs[StatKeys.lastDay] = dayOfYear
        }
    }
}
