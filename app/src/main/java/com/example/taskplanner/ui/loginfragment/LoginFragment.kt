package com.example.taskplanner.ui.loginfragment


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.taskplanner.databinding.LoginFragmentBinding
import com.example.taskplanner.ui.basefragment.BaseFragment

class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> LoginFragmentBinding get() = LoginFragmentBinding::inflate
    override fun getViewModel() = LoginViewModel::class.java
    override fun initFragment(viewModel: LoginViewModel) {

    }


}