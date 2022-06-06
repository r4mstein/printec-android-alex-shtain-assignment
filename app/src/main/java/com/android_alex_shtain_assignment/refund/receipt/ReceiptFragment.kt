package com.android_alex_shtain_assignment.refund.receipt

import android.os.Bundle
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
import com.android_alex_shtain_assignment.refund.receipt.models.ReceiptUiData
import com.android_alex_shtain_assignment.refund.receipt.status.ReceiptActionStatus
import com.android_alex_shtain_assignment.refund.receipt.status.ReceiptUiStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReceiptFragment : BaseFragment<FrReceiptBinding>(FrReceiptBinding::inflate) {

    private val viewModel: ReceiptViewModel by viewModels()

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
                    ReceiptActionStatus.Initial -> {
                        // do nothing
                    }
                    is ReceiptActionStatus.ShowNextFragment -> {
                        activity.enableWindow()
                        hideLoader()
                        navigator.showRefundFragment(
                            activity = requireActivity() as AppCompatActivity,
                            containerId = R.id.fragmentContainer,
                            receiptNumber = actionStatus.receipt,
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
                    ReceiptUiStatus.Initial -> {
                        // do nothing
                    }
                    is ReceiptUiStatus.SetupUI -> {
                        setupUI(uiStatus.data)
                    }
                    ReceiptUiStatus.ShowLoader -> {
                        activity.hideKeyboard(this@ReceiptFragment.view?.rootView)
                        activity.disableWindow()
                        showLoader()
                    }
                    is ReceiptUiStatus.ShowResourcesError -> {
                        activity.enableWindow()
                        hideLoader()
                        requireContext().makeSnackbar(
                            resMessage = uiStatus.error,
                            view = requireView(),
                        ).show()
                    }
                    is ReceiptUiStatus.ShowLastReceipt -> {
                        binding?.tietReceiptNumber?.apply {
                            setText(uiStatus.receipt)
                            setSelection(uiStatus.receipt.length)
                        }
                    }
                    is ReceiptUiStatus.ShowError -> {
                        activity.enableWindow()
                        hideLoader()
                        requireContext().makeSnackbar(
                            textMessage = uiStatus.error,
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
    private fun setupUI(data: ReceiptUiData) {
        activity.enableWindow()
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

            tilReceiptNumber.hint = getString(data.numberHint)

            btnNext.apply {
                text = context.getString(data.btnNextLabel)
                setOnClickListener {
                    data.btnNextClickListener.invoke(tietReceiptNumber.text?.toString().orEmpty())
                }
            }
            btnLastReceipt.apply {
                text = context.getString(data.btnLastReceiptLabel)
                setOnClickListener {
                    data.btnLastReceiptClickListener.invoke()
                }
                if (data.isShowLastReceiptBtn) {
                    showView()
                } else {
                    hideView()
                }
            }
        }
    }

    companion object {

        fun newInstance(): ReceiptFragment {
            return ReceiptFragment()
        }
    }
}