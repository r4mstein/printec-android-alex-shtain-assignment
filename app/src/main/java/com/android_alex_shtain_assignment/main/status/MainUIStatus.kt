package com.android_alex_shtain_assignment.main.status

import com.android_alex_shtain_assignment.main.models.MainUiData

sealed class MainUIStatus {

    object Initial : MainUIStatus()

    class SetupUI(val data: MainUiData) : MainUIStatus()

}
