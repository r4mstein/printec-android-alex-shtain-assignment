package com.android_alex_shtain_assignment.sales.status

import com.android_alex_shtain_assignment.final.models.FinalArguments

sealed class SalesActionStatus {

    object Initial : SalesActionStatus()

    class ShowFinalFragment(
        val args: FinalArguments,
        val descriptionPrefix: Int,
    ) : SalesActionStatus()

}
