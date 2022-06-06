package com.android_alex_shtain_assignment.core.extensions

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.android_alex_shtain_assignment.R
import com.google.android.material.snackbar.Snackbar

/**
 * Create a snackbar with a [resMessage] or a [textMessage]
 *
 * @param resMessage - a message from the string resources
 * @param textMessage - a text message
 * @param view - a view where will be shown a snackbar
 * @param length - length for showing a snackbar
 * @param backgroundTint - a color for a snackbar background
 * @param textColor - a color for a snackbar text
 */
fun Context.makeSnackbar(
    resMessage: Int? = null,
    textMessage: String? = null,
    view: View,
    length: Int = Snackbar.LENGTH_SHORT,
    backgroundTint: Int = R.color.teal_200,
    textColor: Int = R.color.white,
): Snackbar {
    val message: String = when (resMessage) {
        null -> {
            textMessage.orEmpty()
        }
        else -> {
            this.getString(resMessage)
        }
    }

    return Snackbar.make(view, message, length)
        .setBackgroundTint(ContextCompat.getColor(this, backgroundTint))
        .setTextColor(ContextCompat.getColor(this, textColor))
}

