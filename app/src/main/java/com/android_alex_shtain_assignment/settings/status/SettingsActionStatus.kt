package com.android_alex_shtain_assignment.settings.status

sealed class SettingsActionStatus {

    object Initial : SettingsActionStatus()

    object DataSaved : SettingsActionStatus()
}
