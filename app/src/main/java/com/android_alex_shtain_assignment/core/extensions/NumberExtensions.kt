package com.android_alex_shtain_assignment.core.extensions

/**
 * @return a [Double] value or zero
 */
fun Double?.orZero(): Double = this ?: 0.0

/**
 * @return a [Int] value or zero
 */
fun Int?.orZero(): Int = this ?: 0

/**
 * Parses a [String] value to [Int] and return a [Int] value or zero
 */
fun String?.toIntOrZero(): Int = this?.toIntOrNull().orZero()