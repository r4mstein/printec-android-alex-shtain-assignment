package com.android_alex_shtain_assignment.settings

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.core.extensions.toIntOrZero
import com.android_alex_shtain_assignment.core.preferences.PreferencesApi
import com.android_alex_shtain_assignment.settings.models.SettingsEnteredData
import com.android_alex_shtain_assignment.settings.models.SettingsUiData
import com.android_alex_shtain_assignment.settings.status.SettingsActionStatus
import com.android_alex_shtain_assignment.settings.status.SettingsUiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: PreferencesApi,
) : ViewModel() {

    private val _uiStatus = MutableStateFlow<SettingsUiStatus>(SettingsUiStatus.Initial)
    val uiStatus = _uiStatus.asStateFlow()

    private val _actionStatus = MutableStateFlow<SettingsActionStatus>(SettingsActionStatus.Initial)
    val actionStatus = _actionStatus.asStateFlow()

    /**
     * Setup ui data
     */
    fun setupUI() {
        _uiStatus.value = SettingsUiStatus.SetupUI(
            SettingsUiData(
                toolbarTitle = R.string.settings_toolbar_title,
                toolbarIcon = R.drawable.ic_arrow_back,
                minAmountHeader = R.string.settings_min_amount_header,
                minAmountHint = R.string.settings_min_amount_hint,
                minAmountTextChangeListener = { data ->
                    minAmountChanged(data)
                },
                maxAmountHeader = R.string.settings_max_amount_header,
                maxAmountHint = R.string.settings_max_amount_hint,
                maxAmountTextChangeListener = { data ->
                    maxAmountChanged(data)
                },
                hostHeader = R.string.settings_host_header,
                hostHint = R.string.settings_host_hint,
                hostTextChangeListener = { host ->
                    hostChanged(host)
                },
                btnSaveLabel = R.string.settings_btn_save_label,
                btnSaveClickListener = { data ->
                    btnSaveClicked(data)
                },
                minAmount = preferences.getMinAmount().toString(),
                maxAmount = preferences.getMaxAmount().toString(),
                host = preferences.getHost(),
            )
        )
    }

    /**
     * Show an error if an entered min amount is invalid or hide an error otherwise
     */
    private fun minAmountChanged(data: SettingsEnteredData) {
        _uiStatus.value = when (val isMinAmountValid = checkMinAmount(data)) {
            null -> {
                SettingsUiStatus.HideMinAmountError
            }
            else -> {
                SettingsUiStatus.ShowMinAmountError(isMinAmountValid)
            }
        }
    }

    /**
     * Show an error if an entered max amount is invalid or hide an error otherwise
     */
    private fun maxAmountChanged(data: SettingsEnteredData) {
        _uiStatus.value = when (val isMaxAmountValid = checkMaxAmount(data)) {
            null -> {
                SettingsUiStatus.HideMaxAmountError
            }
            else -> {
                SettingsUiStatus.ShowMaxAmountError(isMaxAmountValid)
            }
        }
    }

    /**
     * Show an error if an entered [host] is invalid or hide an error otherwise
     */
    private fun hostChanged(host: String?) {
        _uiStatus.value = when (val isHostValid = checkHost(host)) {
            null -> {
                SettingsUiStatus.HideHostError
            }
            else -> {
                SettingsUiStatus.ShowHostError(isHostValid)
            }
        }
    }

    /**
     * Save an entered [data] if this [data] is valid or show an error otherwise
     */
    private fun btnSaveClicked(data: SettingsEnteredData) {
        val isMinAmountValid = checkMinAmount(data)
        val isMaxAmountValid = checkMaxAmount(data)
        val isHostValid = checkHost(data.host)

        if (isMinAmountValid == null && isMaxAmountValid == null && isHostValid == null) {
            preferences.apply {
                saveMinAmount(data.minAmount.toInt())
                saveMaxAmount(data.maxAmount.toInt())
                saveHost(data.host)
            }
            _actionStatus.value = SettingsActionStatus.DataSaved
        } else {
            _uiStatus.value =
                SettingsUiStatus.ShowAllFieldsError(R.string.settings_all_fields_error)
        }
    }

    /**
     * Check an entered [host]
     *
     * @return a resource id for an error if an [host] is invalid or null otherwise
     */
    private fun checkHost(host: String?): Int? {
        return when {
            host.isNullOrBlank() -> {
                R.string.settings_empty_field_error
            }
            Patterns.WEB_URL.matcher(host).matches().not() -> {
                R.string.settings_host_error
            }
            else -> {
                null
            }
        }
    }

    /**
     * Check an entered max amount
     *
     * @return a resource id for an error if a max amount is invalid or null otherwise
     */
    private fun checkMaxAmount(data: SettingsEnteredData): Int? {
        return when {
            data.maxAmount.isEmpty() -> {
                R.string.settings_empty_field_error
            }
            data.maxAmount.toIntOrZero() < data.minAmount.toIntOrZero() -> {
                R.string.settings_max_amount_error
            }
            else -> {
                null
            }
        }
    }

    /**
     * Check an entered min amount
     *
     * @return a resource id for an error if a min amount is invalid or null otherwise
     */
    private fun checkMinAmount(data: SettingsEnteredData): Int? {
        return when {
            data.minAmount.isEmpty() -> {
                R.string.settings_empty_field_error
            }
            data.minAmount.toIntOrZero() > data.maxAmount.toIntOrZero() -> {
                R.string.settings_min_amount_error
            }
            else -> {
                null
            }
        }
    }
}