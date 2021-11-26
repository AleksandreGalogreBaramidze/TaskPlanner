package com.example.taskplanner.ui.registrationfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.RegisterFragmentBinding
import com.example.taskplanner.ui.basefragment.BaseFragment

class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> RegisterFragmentBinding get() = RegisterFragmentBinding::inflate
    override fun getViewModel() = RegisterViewModel::class.java
    override fun initFragment(viewModel: RegisterViewModel) {

    }
}