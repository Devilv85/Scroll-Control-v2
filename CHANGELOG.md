# Phase 2 complete

- Added subtle overlays for NUDGE/PAUSE with a lightweight overlay controller (no analytics, local only)
- Completed Settings: per-app toggles, overlays toggle, and grace/cooldown sliders with persistence
- Polished detection: per-app thresholds and basic debouncing scaffolding; placeholder rapid scroll heuristic
- Intervention UX: gentle overlay messages; safe BACK/HOME actions for redirect/block
- Stats UI: Today + Streak; ready for 7-day sparkline in Phase 3
- Performance: settings snapshot caching via VM layer and coroutine-based non-blocking updates

All operations remain offline, privacy-first, and minimal.
