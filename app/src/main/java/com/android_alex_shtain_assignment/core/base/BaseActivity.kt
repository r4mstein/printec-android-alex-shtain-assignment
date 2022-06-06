package com.android_alex_shtain_assignment.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: (LayoutInflater) -> VB
) : AppCompatActivity() {

    protected var binding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    /**
     * Clear all fragments from the back stack
     */
    fun clearBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}