package com.android_alex_shtain_assignment.main.status

sealed class MainActionStatus {

    object Initial : MainActionStatus()

    object ShowSales : MainActionStatus()

    object ShowRefund : MainActionStatus()

    object ShowSettings : MainActionStatus()
}