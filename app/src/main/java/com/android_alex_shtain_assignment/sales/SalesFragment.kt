package com.android_alex_shtain_assignment.sales

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.core.base.BaseFragment
import com.android_alex_shtain_assignment.core.extensions.*
import com.android_alex_shtain_assignment.databinding.FrSalesBinding
import com.android_alex_shtain_assignment.sales.models.SalesUiData
import com.android_alex_shtain_assignment.sales.status.SalesActionStatus
import com.android_alex_shtain_assignment.sales.status.SalesUiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SalesFragment : BaseFragment<FrSalesBinding>(FrSalesBinding::inflate) {

    private val viewModel: SalesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setupUI()
        }
        subscribeUiStatuses()
        subscribeActionStatuses()
    }

    /**
     * Subscribe for action statuses
     */
    private fun subscribeActionStatuses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actionStatus.collect { actionStatus ->
                when (actionStatus) {
                    SalesActionStatus.Initial -> {
                        // do nothing
                    }
                    is SalesActionStatus.ShowFinalFragment -> {
                        activity.enableWindow()
                        hideLoader()
                        val arguments = actionStatus.args.copy(
                            description = requireContext().getString(actionStatus.descriptionPrefix)
                                    + actionStatus.args.description
                        )
                        findNavController().navigate(
                            resId = R.id.action_global_finalFragment,
                            args = bundleOf("final_args_key" to arguments)
                        )
                    }
                }
            }
        }
    }

    /**
     * Subscribe for ui statuses
     */
    private fun subscribeUiStatuses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiStatus.collect { uiStatus ->
                when (uiStatus) {
                    SalesUiStatus.Initial -> {
                        // do nothing
                    }
                    is SalesUiStatus.SetupUI -> {
                        setupUI(uiStatus.data)
                    }
                    is SalesUiStatus.ShowAmountError -> {
                        binding?.tilAmount?.apply {
                            error = context.getString(uiStatus.error)
                            isErrorEnabled = true
                        }
                    }
                    SalesUiStatus.HideAmountError -> {
                        binding?.tilAmount?.isErrorEnabled = false
                    }
                    is SalesUiStatus.ShowSnackbarError -> {
                        requireContext().makeSnackbar(
                            resMessage = uiStatus.error,
                            view = requireView(),
                        ).show()
                    }
                    SalesUiStatus.ShowLoader -> {
                        activity.hideKeyboard(this@SalesFragment.view?.rootView)
                        activity.disableWindow()
                        showLoader()
                    }
                }
            }
        }
    }

    private fun showLoader() {
        binding?.loader?.flLoaderContainer?.showView()
    }

    private fun hideLoader() {
        binding?.loader?.flLoaderContainer?.hideView()
    }

    /**
     * Set [data] to ui elements
     */
    private fun setupUI(data: SalesUiData) {
        binding?.apply {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            salesToolbar.toolbar.apply {
                title = context.getString(data.toolbarTitle)
                navigationIcon =
                    ContextCompat.getDrawable(requireContext(), data.toolbarIcon)
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            }

            tilAmount.hint = getString(data.amountHint)
            tietAmount.doAfterTextChanged { amount ->
                data.amountTextChangeListener.invoke(amount?.toString().orEmpty())
            }

            btnNext.apply {
                text = context.getString(data.btnNextLabel)
                setOnClickListener {
                    data.btnNextClickListener.invoke(tietAmount.text?.toString().orEmpty())
                }
            }
        }
    }
}