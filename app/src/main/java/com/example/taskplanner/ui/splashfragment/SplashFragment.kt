package com.example.taskplanner.ui.splashfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.SplashFragmentBinding
import com.example.taskplanner.ui.basefragment.BaseFragment

class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> SplashFragmentBinding get() = SplashFragmentBinding::inflate
    override fun getViewModel() = SplashViewModel::class.java
    override fun initFragment(viewModel: SplashViewModel) {

    }

}