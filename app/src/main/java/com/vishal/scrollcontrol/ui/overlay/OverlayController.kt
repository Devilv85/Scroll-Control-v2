package com.vishal.scrollcontrol.ui.overlay

import android.app.Service
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.widget.TextView

class OverlayController(private val context: Context) {
    private val wm = context.getSystemService(Service.WINDOW_SERVICE) as WindowManager
    private var view: View? = null

    private fun params(): WindowManager.LayoutParams {
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else WindowManager.LayoutParams.TYPE_PHONE
        return WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            y = 160
        }
    }

    fun showMessage(message: String, durationMs: Long = 1500L) {
        hide()
        val tv = TextView(context).apply {
            text = message
            setPadding(28, 16, 28, 16)
            setBackgroundColor(0x99000000.toInt())
            setTextColor(0xFFFFFFFF.toInt())
        }
        view = tv
        wm.addView(tv, params())
        val fade = AlphaAnimation(0f, 1f).apply { duration = 250 }
        tv.startAnimation(fade)
        tv.postDelayed({ hide() }, durationMs)
    }

    fun hide() {
        view?.let {
            runCatching { wm.removeView(it) }
            view = null
        }
    }
}
