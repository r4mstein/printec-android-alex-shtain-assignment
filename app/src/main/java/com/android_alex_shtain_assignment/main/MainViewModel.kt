package com.android_alex_shtain_assignment.main

import androidx.lifecycle.ViewModel
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.main.models.BtnData
import com.android_alex_shtain_assignment.main.models.MainUiData
import com.android_alex_shtain_assignment.main.status.MainActionStatus
import com.android_alex_shtain_assignment.main.status.MainUIStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiStatus = MutableStateFlow<MainUIStatus>(MainUIStatus.Initial)
    val uiStatus = _uiStatus.asStateFlow()

    private val _actionStatus = MutableStateFlow<MainActionStatus>(MainActionStatus.Initial)
    val actionStatus = _actionStatus.asStateFlow()

    /**
     * Setup ui data
     */
    fun setupUI() {
        _actionStatus.value = MainActionStatus.Initial
        _uiStatus.value = MainUIStatus.SetupUI(
            MainUiData(
                salesData = getSalesUiData(),
                refundData = getRefundUiData(),
                settingsData = getSettingsUiData()
            )
        )
    }

    private fun getSettingsUiData() = BtnData(
        label = R.string.main_btn_settings_label,
        clickListener = {
            _actionStatus.value = MainActionStatus.ShowSettings
        }
    )

    private fun getRefundUiData() = BtnData(
        label = R.string.main_btn_refund_label,
        clickListener = {
            _actionStatus.value = MainActionStatus.ShowRefund
        }
    )

    private fun getSalesUiData() = BtnData(
        label = R.string.main_btn_sales_label,
        clickListener = {
            _actionStatus.value = MainActionStatus.ShowSales
        }
    )
}