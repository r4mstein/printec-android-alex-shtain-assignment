package com.android_alex_shtain_assignment.final.status

sealed class FinalActionStatus {

    object Initial : FinalActionStatus()

    object ShowHomeScreen : FinalActionStatus()
}
