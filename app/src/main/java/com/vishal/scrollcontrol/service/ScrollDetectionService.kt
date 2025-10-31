package com.vishal.scrollcontrol.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_FOCUSED
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_SCROLLED
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
import android.view.accessibility.AccessibilityNodeInfo
import dagger.hilt.android.AndroidEntryPoint

private const val PKG_YOUTUBE = "com.google.android.youtube"
private const val PKG_INSTAGRAM = "com.instagram.android"

/**
 * Phase 2: Detection pipeline skeleton with event filtering and signal extraction hooks.
 * Minimal logic now; real actions will be added incrementally.
 */
@AndroidEntryPoint
class ScrollDetectionService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        // Keep defaults from XML; lightweight init only
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val pkg = event.packageName?.toString() ?: return
        if (pkg != PKG_YOUTUBE && pkg != PKG_INSTAGRAM) return

        when (event.eventType) {
            TYPE_WINDOW_STATE_CHANGED, TYPE_VIEW_FOCUSED, TYPE_VIEW_SCROLLED -> {
                val root = rootInActiveWindow ?: return
                val signals = collectSignals(pkg, root)
                // TODO: score signals and drive intervention state machine
                // Placeholder: no-op to keep Phase 2 scaffolding minimal
            }
        }
    }

    override fun onInterrupt() {
        // No-op
    }

    private data class Signals(
        val isShortsOrReelsCandidate: Boolean,
        val matchedIds: List<String>,
        val matchedTexts: List<String>,
        val urlHints: List<String>
    )

    private fun collectSignals(pkg: String, root: AccessibilityNodeInfo): Signals {
        val matchedIds = mutableListOf<String>()
        val matchedTexts = mutableListOf<String>()
        val urlHints = mutableListOf<String>()
        var candidate = false

        fun traverse(node: AccessibilityNodeInfo?) {
            if (node == null) return
            val vid = node.viewIdResourceName
            val text = node.text?.toString()?.lowercase()
            val desc = node.contentDescription?.toString()?.lowercase()

            // Primary ID patterns (to be expanded in later commits)
            if (vid != null) {
                if (pkg == PKG_YOUTUBE && (
                        vid.contains("shorts", ignoreCase = true)
                    )
                ) {
                    matchedIds += vid
                    candidate = true
                }
                if (pkg == PKG_INSTAGRAM && (
                        vid.contains("reel", ignoreCase = true)
                    )
                ) {
                    matchedIds += vid
                    candidate = true
                }
            }

            // Textual cues
            listOfNotNull(text, desc).forEach { t ->
                if (pkg == PKG_YOUTUBE && ("shorts" in t)) {
                    matchedTexts += t
                    candidate = true
                }
                if (pkg == PKG_INSTAGRAM && ("reel" in t || "reels" in t)) {
                    matchedTexts += t
                    candidate = true
                }
                if ("youtube.com/shorts" in t || "instagram.com/reels" in t) {
                    urlHints += t
                    candidate = true
                }
            }

            for (i in 0 until node.childCount) traverse(node.getChild(i))
        }

        traverse(root)
        return Signals(candidate, matchedIds, matchedTexts, urlHints)
    }
}
