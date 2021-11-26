package com.example.taskplanner.ui.homefragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.HomeFragmentBinding
import com.example.taskplanner.ui.basefragment.BaseFragment

class HomeFragment  : BaseFragment<HomeFragmentBinding, HomeViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding get() = HomeFragmentBinding::inflate
    override fun getViewModel() = HomeViewModel::class.java
    override fun initFragment(viewModel: HomeViewModel) {

    }

}