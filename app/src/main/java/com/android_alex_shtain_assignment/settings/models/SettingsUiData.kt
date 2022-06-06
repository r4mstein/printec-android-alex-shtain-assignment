package com.android_alex_shtain_assignment.settings.models

data class SettingsUiData(
    val toolbarTitle: Int,
    val toolbarIcon: Int,
    val minAmount: String,
    val minAmountHeader: Int,
    val minAmountHint: Int,
    val minAmountTextChangeListener: (data: SettingsEnteredData) -> Unit,
    val maxAmount: String,
    val maxAmountHeader: Int,
    val maxAmountHint: Int,
    val maxAmountTextChangeListener: (data: SettingsEnteredData) -> Unit,
    val host: String,
    val hostHeader: Int,
    val hostHint: Int,
    val hostTextChangeListener: (host: String?) -> Unit,
    val btnSaveLabel: Int,
    val btnSaveClickListener: (data: SettingsEnteredData) -> Unit,
)
