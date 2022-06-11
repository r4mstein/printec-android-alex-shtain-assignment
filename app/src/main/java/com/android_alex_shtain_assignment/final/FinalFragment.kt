package com.android_alex_shtain_assignment.final

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android_alex_shtain_assignment.core.base.BaseFragment
import com.android_alex_shtain_assignment.databinding.FrFinalBinding
import com.android_alex_shtain_assignment.final.models.FinalUiData
import com.android_alex_shtain_assignment.final.status.FinalActionStatus
import com.android_alex_shtain_assignment.final.status.FinalUiStatus
import kotlinx.coroutines.launch

class FinalFragment : BaseFragment<FrFinalBinding>(FrFinalBinding::inflate) {

    private val viewModel: FinalViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setupUI(arguments?.getParcelable("final_args_key"))
        }
        subscribeUiStatuses()
        subscribeActionStatuses()
        addOnBackPressedCallback()
    }

    /**
     * Subscribe for ui statuses
     */
    private fun subscribeUiStatuses() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiStatus.collect { uiStatus ->
                when (uiStatus) {
                    FinalUiStatus.Initial -> {
                        // do nothing
                    }
                    is FinalUiStatus.SetupUI -> {
                        setupUI(uiStatus.data)
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
                    FinalActionStatus.Initial -> {
                        // do nothing
                    }
                    FinalActionStatus.ShowHomeScreen -> {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    /**
     * Set [data] to ui elements
     */
    private fun setupUI(data: FinalUiData) {
        binding?.apply {
            ivIcon.setImageResource(data.icon)
            tvDescription.text = data.description
            btnAction.apply {
                text = context.getString(data.btnActionLabel)
                setOnClickListener {
                    data.btnActionClickListener.invoke()
                }
            }
        }
    }

    /**
     * Add a back pressed callback to prevent moving on previous fragments
     */
    private fun addOnBackPressedCallback() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // do nothing
                }
            }
        )
    }
}