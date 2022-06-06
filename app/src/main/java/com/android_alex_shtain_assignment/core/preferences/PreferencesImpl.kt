package com.android_alex_shtain_assignment.core.preferences

import android.content.Context
import com.android_alex_shtain_assignment.core.network.NetworkConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val SETTINGS_PREF = "settings"

private const val MIN_AMOUNT_KEY = "MIN_AMOUNT_KEY"
private const val MAX_AMOUNT_KEY = "MAX_AMOUNT_KEY"
private const val HOST_KEY = "HOST_KEY"
private const val LAST_RECEIPT_KEY = "LAST_RECEIPT_KEY"

private const val MIN_AMOUNT_DEFAULT_VALUE = 50
private const val MAX_AMOUNT_DEFAULT_VALUE = 500

class PreferencesImpl @Inject constructor(@ApplicationContext context: Context) : PreferencesApi {

    val pref = context.getSharedPreferences(SETTINGS_PREF, Context.MODE_PRIVATE)

    /**
     * Save min [amount] for paying
     */
    override fun saveMinAmount(amount: Int) {
        saveInt(MIN_AMOUNT_KEY, amount)
    }

    /**
     * @return min amount for paying
     */
    override fun getMinAmount(): Int {
        return pref.getInt(MIN_AMOUNT_KEY, MIN_AMOUNT_DEFAULT_VALUE)
    }

    /**
     * Save max [amount] for paying
     */
    override fun saveMaxAmount(amount: Int) {
        saveInt(MAX_AMOUNT_KEY, amount)
    }

    /**
     * @return max amount for paying
     */
    override fun getMaxAmount(): Int {
        return pref.getInt(MAX_AMOUNT_KEY, MAX_AMOUNT_DEFAULT_VALUE)
    }

    /**
     * Save [host] url for network requests
     */
    override fun saveHost(host: String) {
        saveString(HOST_KEY, host)
    }

    /**
     * @return a host url for network requests
     */
    override fun getHost(): String {
        return pref.getString(HOST_KEY, NetworkConstants.BASE_URL) ?: NetworkConstants.BASE_URL
    }

    /**
     * Save last [receipt] which was successfully payed
     */
    override fun saveLastReceipt(receipt: String) {
        saveString(LAST_RECEIPT_KEY, receipt)
    }

    /**
     * @return a last receipt which was successfully payed
     */
    override fun getLastReceipt(): String {
        return pref.getString(LAST_RECEIPT_KEY, "").orEmpty()
    }

    private fun saveInt(key: String, value: Int) {
        pref.edit().apply {
            putInt(key, value)
            apply()
        }
    }

    private fun saveString(key: String, value: String) {
        pref.edit().apply {
            putString(key, value)
            apply()
        }
    }
}