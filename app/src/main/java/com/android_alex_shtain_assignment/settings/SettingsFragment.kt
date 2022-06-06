package com.android_alex_shtain_assignment.settings

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android_alex_shtain_assignment.core.base.BaseFragment
import com.android_alex_shtain_assignment.core.extensions.makeSnackbar
import com.android_alex_shtain_assignment.databinding.FrSettingsBinding
import com.android_alex_shtain_assignment.settings.models.SettingsEnteredData
import com.android_alex_shtain_assignment.settings.models.SettingsUiData
import com.android_alex_shtain_assignment.settings.status.SettingsActionStatus
import com.android_alex_shtain_assignment.settings.status.SettingsUiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FrSettingsBinding>(FrSettingsBinding::inflate) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setupUI()
        }
        subscribeUiStatuses()
        subscribeActionStatuses()
    }

    /**
     * Subscribe for ui statuses
     */
    private fun subscribeUiStatuses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiStatus.collect { uiStatus ->
                when (uiStatus) {
                    SettingsUiStatus.Initial -> {
                        // do nothing
                    }
                    is SettingsUiStatus.SetupUI -> {
                        setupUI(uiStatus.data)
                    }
                    SettingsUiStatus.HideHostError -> {
                        binding?.tilHostUrl?.isErrorEnabled = false
                    }
                    SettingsUiStatus.HideMaxAmountError -> {
                        binding?.tilMaxAmount?.isErrorEnabled = false
                    }
                    SettingsUiStatus.HideMinAmountError -> {
                        binding?.tilMinAmount?.isErrorEnabled = false
                    }
                    is SettingsUiStatus.ShowHostError -> {
                        binding?.tilHostUrl?.apply {
                            error = context.getString(uiStatus.error)
                            isErrorEnabled = true
                        }
                    }
                    is SettingsUiStatus.ShowMaxAmountError -> {
                        binding?.tilMaxAmount?.apply {
                            error = context.getString(uiStatus.error)
                            isErrorEnabled = true
                        }
                    }
                    is SettingsUiStatus.ShowMinAmountError -> {
                        binding?.tilMinAmount?.apply {
                            error = context.getString(uiStatus.error)
                            isErrorEnabled = true
                        }
                    }
                    is SettingsUiStatus.ShowAllFieldsError -> {
                        requireContext().makeSnackbar(
                            resMessage = uiStatus.error,
                            view = requireView(),
                        ).show()
                    }
                }
            }
        }
    }

    /**
     * Subscribe for action statuses
     */
    private fun subscribeActionStatuses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actionStatus.collect { actionStatus ->
                when (actionStatus) {
                    SettingsActionStatus.DataSaved -> {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    SettingsActionStatus.Initial -> {
                        // do nothing
                    }
                }
            }
        }
    }

    /**
     * Set [data] to ui elements
     */
    private fun setupUI(data: SettingsUiData) {
        binding?.apply {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            settingsToolbar.toolbar.apply {
                title = context.getString(data.toolbarTitle)
                navigationIcon =
                    ContextCompat.getDrawable(requireContext(), data.toolbarIcon)
                setNavigationOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            tvMinAmountHeader.text = getString(data.minAmountHeader)
            tilMinAmount.hint = getString(data.minAmountHint)
            tietMinAmount.apply {
                setText(data.minAmount)
                doAfterTextChanged {
                    data.minAmountTextChangeListener.invoke(getEnteredData())
                }
            }

            tvMaxAmountHeader.text = getString(data.maxAmountHeader)
            tilMaxAmount.hint = getString(data.maxAmountHint)
            tietMaxAmount.apply {
                setText(data.maxAmount)
                doAfterTextChanged {
                    data.maxAmountTextChangeListener.invoke(getEnteredData())
                }
            }

            tvHostHeader.text = getString(data.hostHeader)
            tilHostUrl.hint = getString(data.hostHint)
            tietHostUrl.apply {
                setText(data.host)
                doAfterTextChanged { host ->
                    data.hostTextChangeListener.invoke(host?.toString())
                }
            }

            btnSave.apply {
                text = context.getString(data.btnSaveLabel)
                setOnClickListener {
                    data.btnSaveClickListener.invoke(getEnteredData())
                }
            }
        }
    }

    /**
     * @return [SettingsEnteredData] - data that was entered by a user
     */
    private fun getEnteredData(): SettingsEnteredData {
        return SettingsEnteredData(
            minAmount = binding?.tietMinAmount?.text?.toString().orEmpty(),
            maxAmount = binding?.tietMaxAmount?.text?.toString().orEmpty(),
            host = binding?.tietHostUrl?.text?.toString().orEmpty(),
        )
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}