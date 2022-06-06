package com.android_alex_shtain_assignment.sales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.core.extensions.orZero
import com.android_alex_shtain_assignment.core.extensions.toIntOrZero
import com.android_alex_shtain_assignment.core.network.NetworkStatus
import com.android_alex_shtain_assignment.core.network.repositories.sales.SalesRepoApi
import com.android_alex_shtain_assignment.core.network.repositories.sales.models.PayResponse
import com.android_alex_shtain_assignment.core.preferences.PreferencesApi
import com.android_alex_shtain_assignment.final.models.FinalArguments
import com.android_alex_shtain_assignment.final.models.FinalType
import com.android_alex_shtain_assignment.sales.models.SalesUiData
import com.android_alex_shtain_assignment.sales.status.SalesActionStatus
import com.android_alex_shtain_assignment.sales.status.SalesUiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val preferences: PreferencesApi,
    private val salesRepo: SalesRepoApi,
) : ViewModel() {

    private val _uiStatus = MutableStateFlow<SalesUiStatus>(SalesUiStatus.Initial)
    val uiStatus = _uiStatus.asStateFlow()

    private val _actionStatus = MutableStateFlow<SalesActionStatus>(SalesActionStatus.Initial)
    val actionStatus = _actionStatus.asStateFlow()

    private val minAmount: Int by lazy {
        preferences.getMinAmount()
    }
    private val maxAmount: Int by lazy {
        preferences.getMaxAmount()
    }

    /**
     * Setup ui data
     */
    fun setupUI() {
        _uiStatus.value = SalesUiStatus.SetupUI(
            data = SalesUiData(
                toolbarTitle = R.string.sales_toolbar_title,
                toolbarIcon = R.drawable.ic_arrow_back,
                amountHint = R.string.sales_amount_hint,
                amountTextChangeListener = { amount ->
                    amountChanged(amount)
                },
                btnNextLabel = R.string.sales_btn_next_label,
                btnNextClickListener = { amount ->
                    btnNextClicked(amount)
                }
            )
        )
    }

    private fun btnNextClicked(amount: String) {
        when (checkAmount(amount)) {
            null -> {
                pay(amount)
            }
            else -> {
                _uiStatus.value = SalesUiStatus.ShowSnackbarError(R.string.sales_next_btn_error)
            }
        }
    }

    /**
     * Make a network request for paying
     *
     * @param amount - an amount for paying
     */
    private fun pay(amount: String) {
        viewModelScope.launch {
            salesRepo.pay(amount).collect { status: NetworkStatus<PayResponse> ->
                when (status) {
                    NetworkStatus.Loading -> {
                        _uiStatus.value = SalesUiStatus.ShowLoader
                    }
                    NetworkStatus.NoInternet -> {
                        _uiStatus.value = SalesUiStatus.ShowSnackbarError(
                            R.string.error_no_internet
                        )
                    }
                    is NetworkStatus.Error -> {
                        _actionStatus.value = SalesActionStatus.ShowFinalFragment(
                            args = FinalArguments(
                                finalType = FinalType.FAIL,
                                description = " ${status.message}",
                            ),
                            descriptionPrefix = R.string.final_pay_fail_prefix,
                        )
                    }
                    is NetworkStatus.Success<PayResponse> -> {
                        val data = status.data
                        preferences.saveLastReceipt(data.receiptNumber)
                        _actionStatus.value = SalesActionStatus.ShowFinalFragment(
                            args = FinalArguments(
                                finalType = FinalType.SUCCESS,
                                description = " ${data.amount} ${data.currency}"
                            ),
                            descriptionPrefix = R.string.final_pay_success_prefix,
                        )
                    }
                }
            }
        }
    }

    /**
     * Show an error if an entered [amount] is invalid or hide an error otherwise
     */
    private fun amountChanged(amount: String) {
        _uiStatus.value = when (val isAmountValid = checkAmount(amount)) {
            null -> {
                SalesUiStatus.HideAmountError
            }
            else -> {
                SalesUiStatus.ShowAmountError(isAmountValid)
            }
        }
    }

    /**
     * Check an entered [amount]
     *
     * @return a resource id for an error if an [amount] is invalid or null otherwise
     */
    private fun checkAmount(amount: String): Int? {
        return when {
            amount.isEmpty() -> {
                R.string.sales_empty_field_error
            }
            amount.toIntOrZero() < minAmount.orZero() -> {
                R.string.sales_min_amount_error
            }
            amount.toIntOrZero() > maxAmount.orZero() -> {
                R.string.sales_max_amount_error
            }
            else -> {
                null
            }
        }
    }
}