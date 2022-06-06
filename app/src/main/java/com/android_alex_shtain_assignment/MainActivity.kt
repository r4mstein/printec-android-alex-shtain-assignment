package com.android_alex_shtain_assignment

import android.os.Bundle
import com.android_alex_shtain_assignment.core.base.BaseActivity
import com.android_alex_shtain_assignment.core.navigator.NavigatorApi
import com.android_alex_shtain_assignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    @Inject
    lateinit var navigator: NavigatorApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigator.showMainFragment(
            activity = this,
            containerId = R.id.fragmentContainer,
        )
    }
}