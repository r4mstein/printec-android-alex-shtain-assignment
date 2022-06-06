package com.android_alex_shtain_assignment.sales.status

import com.android_alex_shtain_assignment.sales.models.SalesUiData

sealed class SalesUiStatus {

    object Initial : SalesUiStatus()

    class SetupUI(val data: SalesUiData) : SalesUiStatus()

    class ShowAmountError(val error: Int) : SalesUiStatus()

    object HideAmountError : SalesUiStatus()

    class ShowSnackbarError(val error: Int) : SalesUiStatus()

    object ShowLoader : SalesUiStatus()

}
