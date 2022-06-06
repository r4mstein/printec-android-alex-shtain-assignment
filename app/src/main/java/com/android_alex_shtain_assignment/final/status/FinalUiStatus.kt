package com.android_alex_shtain_assignment.final.status

import com.android_alex_shtain_assignment.final.models.FinalUiData

sealed class FinalUiStatus {

    object Initial : FinalUiStatus()

    class SetupUI(val data: FinalUiData) : FinalUiStatus()

}
