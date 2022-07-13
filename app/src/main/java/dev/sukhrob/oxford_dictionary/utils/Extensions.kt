package dev.sukhrob.oxford_dictionary.utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.setBackgroundTint(Color.parseColor("#E66E7C"))
    snackBar.setActionTextColor(Color.parseColor("#ffffff"))
    snackBar.setTextColor(Color.parseColor("#ffffff"))
    snackBar.show()
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.7f
}