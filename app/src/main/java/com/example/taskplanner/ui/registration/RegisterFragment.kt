package com.example.taskplanner.ui.registration

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.taskplanner.R
import com.example.taskplanner.databinding.RegisterFragmentBinding
import com.example.taskplanner.extensions.animation
import com.example.taskplanner.extensions.observeData
import com.example.taskplanner.extensions.toast
import com.example.taskplanner.ui.basefragment.BaseFragment
import com.example.taskplanner.utils.Constants.ANIMATION_DELAY
import com.example.taskplanner.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> RegisterFragmentBinding get() = RegisterFragmentBinding::inflate
    override fun getViewModel() = RegisterViewModel::class.java
    private var profileImageUri: Uri? = null
    private var isPhotoPicked = false
    override fun initFragment(viewModel: RegisterViewModel) {
        registration(viewModel)
        observer(viewModel)
        setListeners()
        setAnimations()
    }

    private fun registerToLoginNavigation() {
        with(binding.root){
            animation(R.anim.fade_out)
            binding.root.isClickable = false
        }
        lifecycleScope.launch {
            delay(ANIMATION_DELAY)
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun setListeners() {
        binding.goToSignInTextView.setOnClickListener {
            registerToLoginNavigation()
        }
        binding.ProfilePictureImageView.setOnClickListener { openIntent() }
    }
    private fun registration(viewModel: RegisterViewModel) {
        with(binding) {
            signUpButton.setOnClickListener {
                if (emailEditText.text!!.trim().isEmpty() and nameEditText.text!!.trim()
                        .isEmpty() and passwordEditText.text!!.trim()
                        .isEmpty() and repeatPasswordEditText.text!!.trim()
                        .isEmpty() and isPhotoPicked

                ) {
                    getString(R.string.fill_error).toast(requireContext())
                } else {
                    if (passwordEditText.text.toString() == repeatPasswordEditText.text.toString()) {
                        profileImageUri?.let { it1 ->
                            viewModel.signUp(
                                emailEditText.text.toString(),
                                passwordEditText.text.toString(),
                                nameEditText.text.toString(),
                                it1
                            )
                        }
                    } else {
                        getString(R.string.password_error).toast(requireContext())
                    }
                }
            }
        }
    }

    private fun observer(viewModel: RegisterViewModel) {
        observeData(viewModel.signUpResponse) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar2.isVisible = false
                    registerToLoginNavigation()
                }
                is Resource.Error -> {
                    binding.progressBar2.isVisible = false
                    it.errorMessage?.toast(requireContext())
                }
                is Resource.Loading -> {
                    binding.progressBar2.isVisible = true
                }
            }
        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            isPhotoPicked = true
            val uri = result.uriContent
            uri?.let {
                binding.ProfilePictureImageView.setImageURI(it)
                profileImageUri = it
            }
        }
    }

    private fun openIntent() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y)
            }
        )
    }

    private fun setAnimations() {
        with(binding) {
            ProfilePictureImageView.animation(R.anim.bottomanimation)
            blueBackGround.animation(R.anim.bottomanimation)
            addYouPicture.animation(R.anim.bottomanimation)
            welcomeTextView.animation(R.anim.top_animation)
            enterNameTextInputLayout.animation(R.anim.top_animation)
            enterEmailTextInputLayout.animation(R.anim.top_animation)
            repeatPasswordTextInputLayout.animation(R.anim.top_animation)
            passwordTextInputLayout.animation(R.anim.top_animation)
            alreadyHaveAccount.animation(R.anim.top_animation)
            goToSignInTextView.animation(R.anim.top_animation)
            signUpButton.animation(R.anim.top_animation)
        }
    }

    companion object{
        const val ASPECT_RATIO_X = 1
        const val ASPECT_RATIO_Y = 1
    }
}