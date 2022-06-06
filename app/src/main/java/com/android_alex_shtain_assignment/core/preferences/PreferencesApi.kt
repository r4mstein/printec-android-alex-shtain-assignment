package com.android_alex_shtain_assignment.core.preferences

interface PreferencesApi {

    fun saveMinAmount(amount: Int)

    fun getMinAmount(): Int

    fun saveMaxAmount(amount: Int)

    fun getMaxAmount(): Int

    fun saveHost(host: String)

    fun getHost(): String

    fun saveLastReceipt(receipt: String)

    fun getLastReceipt(): String

}