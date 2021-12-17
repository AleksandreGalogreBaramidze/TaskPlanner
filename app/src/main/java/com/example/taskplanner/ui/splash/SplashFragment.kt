package com.example.taskplanner.ui.splash

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.databinding.SplashFragmentBinding
import com.example.taskplanner.extensions.animation
import com.example.taskplanner.extensions.observeData
import com.example.taskplanner.extensions.toast
import com.example.taskplanner.ui.basefragment.BaseFragment
import com.example.taskplanner.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashFragmentBinding, SplashViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> SplashFragmentBinding get() = SplashFragmentBinding::inflate
    override fun getViewModel() = SplashViewModel::class.java
    override fun initFragment(viewModel: SplashViewModel) {
        viewModel.setCurrentUserInfo()
        setAnimations()
        //TODO aqvs firebases es funcqia
        observeData(viewModel.loggedUserInfo) {
            if (it.isLogged) {
                viewModel.logIn(it.loggedUserEmail, it.loggedUserPassword)
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(SPLASH_DELAY)
                    findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
                }
            }
        }

        observeData(viewModel.logInResponse) { response ->
            when (response) {
                is Resource.Success -> {
                    setAnimations()
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(SPLASH_DELAY)
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                }

                is Resource.Error -> {
                    setAnimations()
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(SPLASH_DELAY)
                        findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
                    }
                    response.errorMessage?.let { message ->
                        (message).toast(
                            requireContext()
                        )
                    }
                }

            }
        }
    }

    private fun setAnimations() {
        with(binding) {
            imageView.animation(R.anim.fade_out)
            imageView2.animation(R.anim.fade_out)
        }
    }

    companion object {
        private const val SPLASH_DELAY = 2000L
    }

}