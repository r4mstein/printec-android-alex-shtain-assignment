package com.android_alex_shtain_assignment.refund.amount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.core.extensions.toIntOrZero
import com.android_alex_shtain_assignment.core.network.NetworkStatus
import com.android_alex_shtain_assignment.core.network.repositories.refund.RefundRepoApi
import com.android_alex_shtain_assignment.final.models.FinalArguments
import com.android_alex_shtain_assignment.final.models.FinalType
import com.android_alex_shtain_assignment.refund.amount.models.RefundAmountUiData
import com.android_alex_shtain_assignment.refund.amount.status.RefundAmountActionStatus
import com.android_alex_shtain_assignment.refund.amount.status.RefundAmountUiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RefundAmountViewModel @Inject constructor(
    private val refundRepo: RefundRepoApi,
) : ViewModel() {

    private val _uiStatus = MutableStateFlow<RefundAmountUiStatus>(RefundAmountUiStatus.Initial)
    val uiStatus = _uiStatus.asStateFlow()

    private val _actionStatus =
        MutableStateFlow<RefundAmountActionStatus>(RefundAmountActionStatus.Initial)
    val actionStatus = _actionStatus.asStateFlow()

    /**
     * Setup ui data
     */
    fun setupUI() {
        _uiStatus.value = RefundAmountUiStatus.SetupUI(
            data = RefundAmountUiData(
                toolbarTitle = R.string.refund_toolbar_title,
                toolbarIcon = R.drawable.ic_arrow_back,
                amountHint = R.string.refund_amount_hint,
                btnNextLabel = R.string.refund_btn_next_label,
                btnNextClickListener = { receipt, amount ->
                    btnNextClicked(receiptNumber = receipt, amount = amount)
                }
            )
        )
    }

    private fun btnNextClicked(receiptNumber: String, amount: String) {
        if (amount.toIntOrZero() > 0) {
            refund(receiptNumber, amount)
        } else {
            _uiStatus.value = RefundAmountUiStatus.ShowResourcesError(R.string.refund_amount_error)
        }
    }

    /**
     * Make a refund network request
     */
    private fun refund(receiptNumber: String, amount: String) {
        viewModelScope.launch {
            refundRepo.refund(receiptNumber = receiptNumber, amount = amount)
                .collect { status ->
                    when (status) {
                        NetworkStatus.Loading -> {
                            _uiStatus.value = RefundAmountUiStatus.ShowLoader
                        }
                        NetworkStatus.NoInternet -> {
                            _uiStatus.value = RefundAmountUiStatus.ShowResourcesError(
                                R.string.error_no_internet
                            )
                        }
                        is NetworkStatus.Error -> {
                            _actionStatus.value = RefundAmountActionStatus.ShowFinalFragment(
                                args = FinalArguments(
                                    finalType = FinalType.FAIL,
                                    description = " ${status.message}",
                                ),
                                descriptionPrefix = R.string.final_refund_fail_prefix,
                            )
                        }
                        is NetworkStatus.Success -> {
                            val data = status.data
                            _actionStatus.value = RefundAmountActionStatus.ShowFinalFragment(
                                args = FinalArguments(
                                    finalType = FinalType.SUCCESS,
                                    description = " ${data.amount} ${data.currency}"
                                ),
                                descriptionPrefix = R.string.final_refund_success_prefix,
                            )
                        }
                    }
                }
        }
    }
}