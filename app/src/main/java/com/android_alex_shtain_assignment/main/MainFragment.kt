package com.android_alex_shtain_assignment.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android_alex_shtain_assignment.R
import com.android_alex_shtain_assignment.core.base.BaseFragment
import com.android_alex_shtain_assignment.core.navigator.NavigatorApi
import com.android_alex_shtain_assignment.databinding.FrMainBinding
import com.android_alex_shtain_assignment.main.models.MainUiData
import com.android_alex_shtain_assignment.main.status.MainActionStatus
import com.android_alex_shtain_assignment.main.status.MainUIStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<FrMainBinding>(FrMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

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
                    MainActionStatus.Initial -> {
                        // do nothing
                    }
                    MainActionStatus.ShowSales -> {
                        navigator.showSalesFragment(
                            activity = requireActivity() as AppCompatActivity,
                            containerId = R.id.fragmentContainer
                        )
                    }
                    MainActionStatus.ShowRefund -> {
                        navigator.showReceiptFragment(
                            activity = requireActivity() as AppCompatActivity,
                            containerId = R.id.fragmentContainer
                        )
                    }
                    MainActionStatus.ShowSettings -> {
                        navigator.showSettingsFragment(
                            activity = requireActivity() as AppCompatActivity,
                            containerId = R.id.fragmentContainer
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
                    MainUIStatus.Initial -> {
                        // do nothing
                    }
                    is MainUIStatus.SetupUI -> {
                        setupUI(uiStatus.data)
                    }
                }
            }
        }
    }

    /**
     * Set [data] to ui elements
     */
    private fun setupUI(data: MainUiData) {
        binding?.apply {
            btnSales.apply {
                text = context.getString(data.salesData.label)
                setOnClickListener {
                    data.salesData.clickListener.invoke()
                }
            }

            btnRefund.apply {
                text = context.getString(data.refundData.label)
                setOnClickListener {
                    data.refundData.clickListener.invoke()
                }
            }

            btnSettings.apply {
                text = context.getString(data.settingsData.label)
                setOnClickListener {
                    data.settingsData.clickListener.invoke()
                }
            }
        }
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}