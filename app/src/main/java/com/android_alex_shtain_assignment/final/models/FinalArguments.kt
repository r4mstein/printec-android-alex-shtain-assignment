package com.android_alex_shtain_assignment.final.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FinalArguments(
    val finalType: FinalType,
    val description: String,
) : Parcelable