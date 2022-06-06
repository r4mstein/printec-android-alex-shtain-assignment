package com.android_alex_shtain_assignment.refund.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.core.network.NetworkStatus
import com.android_alex_shtain_assignment.core.network.repositories.refund.RefundRepoApi
import com.android_alex_shtain_assignment.core.preferences.PreferencesApi
import com.android_alex_shtain_assignment.refund.receipt.models.ReceiptUiData
import com.android_alex_shtain_assignment.refund.receipt.status.ReceiptActionStatus
import com.android_alex_shtain_assignment.refund.receipt.status.ReceiptUiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val preferences: PreferencesApi,
    private val refundRepo: RefundRepoApi,
) : ViewModel() {

    private val _uiStatus = MutableStateFlow<ReceiptUiStatus>(ReceiptUiStatus.Initial)
    val uiStatus = _uiStatus.asStateFlow()

    private val _actionStatus = MutableStateFlow<ReceiptActionStatus>(ReceiptActionStatus.Initial)
    val actionStatus = _actionStatus.asStateFlow()

    /**
     * Setup ui data
     */
    fun setupUI() {
        _actionStatus.value = ReceiptActionStatus.Initial
        val lastReceipt = preferences.getLastReceipt()
        _uiStatus.value = ReceiptUiStatus.SetupUI(
            data = ReceiptUiData(
                toolbarTitle = R.string.receipt_toolbar_title,
                toolbarIcon = R.drawable.ic_arrow_back,
                numberHint = R.string.receipt_number_hint,
                btnNextLabel = R.string.receipt_btn_next_label,
                btnNextClickListener = { receipt ->
                    checkReceipt(receipt)
                },
                isShowLastReceiptBtn = lastReceipt.isNotBlank(),
                btnLastReceiptLabel = R.string.receipt_btn_last_label,
                btnLastReceiptClickListener = {
                    _uiStatus.value = ReceiptUiStatus.ShowLastReceipt(lastReceipt)
                    checkReceipt(lastReceipt)
                }
            )
        )
    }

    /**
     * Make a network request for checking [receiptNumber]
     */
    private fun checkReceipt(receiptNumber: String) {
        if (receiptNumber.isBlank()) {
            _uiStatus.value = ReceiptUiStatus.ShowResourcesError(R.string.receipt_empty_error)
        } else {
            viewModelScope.launch {
                refundRepo.checkReceipt(receiptNumber).collect { status: NetworkStatus<Int> ->
                    when (status) {
                        NetworkStatus.Loading -> {
                            _uiStatus.value = ReceiptUiStatus.ShowLoader
                        }
                        NetworkStatus.NoInternet -> {
                            _uiStatus.value = ReceiptUiStatus.ShowResourcesError(
                                R.string.error_no_internet
                            )
                        }
                        is NetworkStatus.Error -> {
                            _uiStatus.value = ReceiptUiStatus.ShowError(status.message)
                        }
                        is NetworkStatus.Success -> {
                            _actionStatus.value =
                                ReceiptActionStatus.ShowNextFragment(receiptNumber)
                        }
                    }
                }
            }
        }
    }
}