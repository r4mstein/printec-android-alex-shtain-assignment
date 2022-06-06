package com.android_alex_shtain_assignment.core.extensions

import android.view.View

/**
 * Show a [View] if this [View] is not visible
 */
fun View.showView() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

/**
 * Hide a [View] if this [View] is not gone
 */
fun View.hideView() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}