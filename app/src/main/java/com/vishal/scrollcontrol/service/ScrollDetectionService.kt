package com.vishal.scrollcontrol.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.vishal.scrollcontrol.core.SettingsRepository
import com.vishal.scrollcontrol.data.StatsRepository
import com.vishal.scrollcontrol.domain.InterventionController
import com.vishal.scrollcontrol.domain.Stage
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

private const val PKG_YOUTUBE = "com.google.android.youtube"
private const val PKG_INSTAGRAM = "com.instagram.android"

@AndroidEntryPoint
class ScrollDetectionService : AccessibilityService() {

    @Inject lateinit var settings: SettingsRepository
    @Inject lateinit var stats: StatsRepository
    private val controller = InterventionController()

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val pkg = event.packageName?.toString() ?: return
        if (pkg != PKG_YOUTUBE && pkg != PKG_INSTAGRAM) return

        val root = rootInActiveWindow ?: return
        val score = scoreSignals(pkg, root)
        if (score < 0.6f) return

        val now = System.currentTimeMillis()
        val next = controller.update(
            packageName = pkg,
            now = now,
            graceMs = 5 * 60_000L,
            cooldownMs = 60 * 60_000L
        )

        when (next.stage) {
            Stage.GRACE -> Unit
            Stage.NUDGE -> gentleNudge()
            Stage.PAUSE -> mindfulPause()
            Stage.REDIRECT -> {
                tryRedirect(pkg)
                incrementToday()
            }
            Stage.BLOCK -> {
                enforceCooldown(pkg)
                incrementToday()
            }
        }
    }

    override fun onInterrupt() {}

    private fun scoreSignals(pkg: String, root: AccessibilityNodeInfo): Float {
        var idHits = 0
        var textHits = 0
        var urlHits = 0

        fun visit(node: AccessibilityNodeInfo?) {
            if (node == null) return
            node.viewIdResourceName?.let { vid ->
                if (pkg == PKG_YOUTUBE && vid.contains("short", true)) idHits++
                if (pkg == PKG_INSTAGRAM && (vid.contains("reel", true) || vid.contains("reels", true))) idHits++
            }
            listOfNotNull(node.text?.toString(), node.contentDescription?.toString()).forEach { t ->
                val low = t.lowercase()
                if (pkg == PKG_YOUTUBE && ("shorts" in low)) textHits++
                if (pkg == PKG_INSTAGRAM && ("reel" in low || "reels" in low)) textHits++
                if ("youtube.com/shorts" in low || "instagram.com/reels" in low) urlHits++
            }
            for (i in 0 until node.childCount) visit(node.getChild(i))
        }

        visit(root)
        val weighted = idHits * 0.5f + textHits * 0.3f + urlHits * 0.2f
        return 1f - (1f / (1f + weighted))
    }

    private fun gentleNudge() { /* TODO overlay/haptic */ }
    private fun mindfulPause() { /* TODO overlay */ }

    private fun tryRedirect(pkg: String) {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }

    private fun enforceCooldown(pkg: String) {
        performGlobalAction(GLOBAL_ACTION_HOME)
    }

    private fun incrementToday() {
        val cal = Calendar.getInstance()
        val dayOfYear = cal.get(Calendar.DAY_OF_YEAR)
        // Best-effort; this is a service context, ensure non-blocking in future with coroutine scope
        try {
            // Placeholder; will be moved to coroutine scope in subsequent commit
            stats.markIntervention(dayOfYear)
        } catch (_: Throwable) { }
    }
}
