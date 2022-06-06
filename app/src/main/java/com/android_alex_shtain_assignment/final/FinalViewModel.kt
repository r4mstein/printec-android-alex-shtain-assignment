package com.android_alex_shtain_assignment.final

import androidx.lifecycle.ViewModel
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.final.models.FinalArguments
import com.android_alex_shtain_assignment.final.models.FinalUiData
import com.android_alex_shtain_assignment.final.status.FinalActionStatus
import com.android_alex_shtain_assignment.final.status.FinalUiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FinalViewModel @Inject constructor() : ViewModel() {

    private val _uiStatus = MutableStateFlow<FinalUiStatus>(FinalUiStatus.Initial)
    val uiStatus = _uiStatus.asStateFlow()

    private val _actionStatus = MutableStateFlow<FinalActionStatus>(FinalActionStatus.Initial)
    val actionStatus = _actionStatus.asStateFlow()

    /**
     * Setup ui data
     */
    fun setupUI(arguments: FinalArguments?) {
        _uiStatus.value = FinalUiStatus.SetupUI(
            data = FinalUiData(
                icon = arguments?.finalType?.icon ?: R.drawable.ic_final_success,
                description = arguments?.description.orEmpty(),
                btnActionLabel = R.string.final_next_btn_label,
                btnActionClickListener = {
                    _actionStatus.value = FinalActionStatus.ShowHomeScreen
                }
            )
        )
    }
}