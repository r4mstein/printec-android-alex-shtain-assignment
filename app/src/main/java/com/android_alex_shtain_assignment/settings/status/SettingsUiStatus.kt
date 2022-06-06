package com.android_alex_shtain_assignment.settings.status

import com.android_alex_shtain_assignment.settings.models.SettingsUiData

sealed class SettingsUiStatus {

    object Initial : SettingsUiStatus()

    class SetupUI(val data: SettingsUiData) : SettingsUiStatus()

    class ShowMinAmountError(val error: Int) : SettingsUiStatus()

    object HideMinAmountError : SettingsUiStatus()

    class ShowMaxAmountError(val error: Int) : SettingsUiStatus()

    object HideMaxAmountError : SettingsUiStatus()

    class ShowHostError(val error: Int) : SettingsUiStatus()

    object HideHostError : SettingsUiStatus()

    class ShowAllFieldsError(val error: Int) : SettingsUiStatus()
}
