package com.android_alex_shtain_assignment.main.models

data class MainUiData(
    val salesData: BtnData,
    val refundData: BtnData,
    val settingsData: BtnData,
)

data class BtnData(
    val label: Int,
    val clickListener: () -> Unit,
)