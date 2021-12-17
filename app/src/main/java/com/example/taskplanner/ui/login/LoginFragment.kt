package com.example.taskplanner.ui.login


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.databinding.LoginFragmentBinding
import com.example.taskplanner.extensions.animation
import com.example.taskplanner.extensions.observeData
import com.example.taskplanner.extensions.startActionAfterAnimation
import com.example.taskplanner.extensions.toast
import com.example.taskplanner.ui.basefragment.BaseFragment
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> LoginFragmentBinding get() = LoginFragmentBinding::inflate
    override fun getViewModel() = LoginViewModel::class.java
    override fun initFragment(viewModel: LoginViewModel) {
        logIn(viewModel)
        observe(viewModel)
        setListeners()
        setAnimations()
    }

    private fun setListeners() {
        binding.signInButton.setOnClickListener {
            with(binding.root) {
                startActionAfterAnimation(R.anim.fade_out) {
                    findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
                }
                binding.root.isClickable = false
            }
        }
    }

    private fun logIn(viewModel: LoginViewModel) {
        with(binding) {
            logInButton.setOnClickListener {
                if (emailEditText.text!!.trim().isEmpty() and passwordEditText.text!!.trim()
                        .isEmpty()
                ) {
                    getString(R.string.fill_error).toast(requireContext())
                } else {
                    viewModel.logIn(emailEditText.text.toString(), passwordEditText.text.toString())
                }
            }
        }

    }

    private fun observe(viewModel: LoginViewModel) {
        observeData(viewModel.logInResponse) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
                is Resource.Error -> {
                    binding.progressBar.isVisible = false
                    it.errorMessage?.let { message -> (message).toast(requireContext()) }
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }

    private fun setAnimations() {
        with(binding) {
            logoImageView.animation(R.anim.bottomanimation)
            blueBackGround.animation(R.anim.bottomanimation)
            welcomeTextView.animation(R.anim.top_animation)
            enterEmailTextInputLayout.animation(R.anim.top_animation)
            enterPasswordTextInputLayout.animation(R.anim.top_animation)
            dontHaveAccountTextView.animation(R.anim.top_animation)
            signInButton.animation(R.anim.top_animation)
            logInButton.animation(R.anim.top_animation)
        }
    }


}