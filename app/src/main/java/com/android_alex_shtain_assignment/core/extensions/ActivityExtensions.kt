package com.android_alex_shtain_assignment.core.extensions

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Add a [fragment]
 *
 * @param containerId - a container where a [fragment] will be added
 * @param fragment - a fragment for adding
 */
fun AppCompatActivity?.addFragment(containerId: Int, fragment: Fragment) {
    this?.supportFragmentManager
        ?.beginTransaction()
        ?.add(containerId, fragment)
        ?.commit()
}

/**
 * Replace a [fragment]
 *
 * @param containerId - a container where a [fragment] will be added
 * @param fragment - a fragment for replacing
 */
fun AppCompatActivity?.replaceFragment(containerId: Int, fragment: Fragment) {
    this?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(containerId, fragment)
        ?.commit()
}

/**
 * Replace a [fragment] and add it to the back stack
 *
 * @param containerId - a container where a [fragment] will be added
 * @param fragment - a fragment for replacing
 */
fun AppCompatActivity?.replaceFragmentAndAddToBackStack(containerId: Int, fragment: Fragment) {
    this?.supportFragmentManager
        ?.beginTransaction()
        ?.replace(containerId, fragment)
        ?.addToBackStack(null)
        ?.commit()
}

/**
 * Hide keyboard
 */
fun Activity?.hideKeyboard(view: View?) {
    (this?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.apply {
        hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

/**
 * Disable the window for touching
 */
fun Activity?.disableWindow() {
    this?.window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

/**
 * Enable the window for touching
 */
fun Activity?.enableWindow() {
    this?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}