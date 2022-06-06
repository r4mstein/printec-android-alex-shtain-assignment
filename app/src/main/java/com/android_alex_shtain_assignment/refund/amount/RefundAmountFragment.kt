package com.android_alex_shtain_assignment.refund.amount

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.core.base.BaseFragment
import com.android_alex_shtain_assignment.core.extensions.*
import com.android_alex_shtain_assignment.core.navigator.NavigatorApi
import com.android_alex_shtain_assignment.databinding.FrReceiptBinding
import com.android_alex_shtain_assignment.refund.amount.models.RefundAmountUiData
import com.android_alex_shtain_assignment.refund.amount.status.RefundAmountActionStatus
import com.android_alex_shtain_assignment.refund.amount.status.RefundAmountUiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RefundAmountFragment : BaseFragment<FrReceiptBinding>(FrReceiptBinding::inflate) {

    private val viewModel: RefundAmountViewModel by viewModels()

    @Inject
    lateinit var navigator: NavigatorApi

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
                    RefundAmountActionStatus.Initial -> {
                        // do nothing
                    }
                    is RefundAmountActionStatus.ShowFinalFragment -> {
                        activity.enableWindow()
                        hideLoader()
                        navigator.showFinalFragment(
                            activity = requireActivity() as AppCompatActivity,
                            containerId = R.id.fragmentContainer,
                            args = actionStatus.args.copy(
                                description = requireContext().getString(actionStatus.descriptionPrefix)
                                        + actionStatus.args.description
                            )
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
                    RefundAmountUiStatus.Initial -> {
                        // do nothing
                    }
                    is RefundAmountUiStatus.SetupUI -> {
                        setupUI(uiStatus.data)
                    }
                    is RefundAmountUiStatus.ShowError -> {
                        activity.hideKeyboard(this@RefundAmountFragment.view?.rootView)
                        activity.enableWindow()
                        hideLoader()
                        requireContext().makeSnackbar(
                            textMessage = uiStatus.error,
                            view = requireView(),
                        ).show()
                    }
                    RefundAmountUiStatus.ShowLoader -> {
                        activity.hideKeyboard(this@RefundAmountFragment.view?.rootView)
                        activity.disableWindow()
                        showLoader()
                    }
                    is RefundAmountUiStatus.ShowResourcesError -> {
                        activity.hideKeyboard(this@RefundAmountFragment.view?.rootView)
                        activity.enableWindow()
                        hideLoader()
                        requireContext().makeSnackbar(
                            resMessage = uiStatus.error,
                            view = requireView(),
                        ).show()
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
    private fun setupUI(data: RefundAmountUiData) {
        binding?.apply {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            receiptToolbar.toolbar.apply {
                title = context.getString(data.toolbarTitle)
                navigationIcon =
                    ContextCompat.getDrawable(requireContext(), data.toolbarIcon)
                setNavigationOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            tilReceiptNumber.hint = getString(data.amountHint)
            tietReceiptNumber.inputType = InputType.TYPE_CLASS_NUMBER

            btnNext.apply {
                text = context.getString(data.btnNextLabel)
                setOnClickListener {
                    data.btnNextClickListener.invoke(
                        arguments?.getString(RECEIPT_NUMBER_KEY).orEmpty(),
                        tietReceiptNumber.text?.toString().orEmpty()
                    )
                }
            }
            btnLastReceipt.hideView()
        }
    }

    companion object {

        private const val RECEIPT_NUMBER_KEY = "receipt_number_key"

        fun newInstance(receiptNumber: String): RefundAmountFragment {
            return RefundAmountFragment().apply {
                arguments = Bundle().apply {
                    putString(RECEIPT_NUMBER_KEY, receiptNumber)
                }
            }
        }
    }
}