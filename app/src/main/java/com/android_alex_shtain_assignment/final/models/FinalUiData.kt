package com.android_alex_shtain_assignment.final.models

data class FinalUiData(
    val icon: Int,
    val description: String,
    val btnActionLabel: Int,
    val btnActionClickListener: () -> Unit,
)
