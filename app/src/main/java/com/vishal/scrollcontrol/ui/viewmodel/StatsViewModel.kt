package com.vishal.scrollcontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishal.scrollcontrol.data.StatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val stats: StatsRepository
) : ViewModel() {

    private val _today = MutableStateFlow(0)
    val todayCount: StateFlow<Int> = _today

    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak

    init {
        viewModelScope.launch { stats.todayCount.collectLatest { _today.value = it } }
        // placeholder: expose real streak when repository provides it
    }
}
