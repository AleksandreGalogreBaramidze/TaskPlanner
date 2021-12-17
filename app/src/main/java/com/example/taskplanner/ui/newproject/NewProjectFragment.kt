package com.example.taskplanner.ui.newproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.taskplanner.R
import com.example.taskplanner.databinding.NewProjectFragmentBinding
import com.example.taskplanner.extensions.animation
import com.example.taskplanner.extensions.observeData
import com.example.taskplanner.extensions.startActionAfterAnimation
import com.example.taskplanner.extensions.toast
import com.example.taskplanner.ui.basefragment.BaseFragment
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Constants.DATE_FORMATTER
import com.example.taskplanner.utils.Constants.FADE_OUT_TRANSACTION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewProjectFragment : BaseFragment<NewProjectFragmentBinding, NewProjectViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> NewProjectFragmentBinding get() = NewProjectFragmentBinding::inflate
    override fun getViewModel() = NewProjectViewModel::class.java
    override fun initFragment(viewModel: NewProjectViewModel) {
        val today = Calendar.getInstance()
        setAnimation()
        binding.datePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            viewModel.dataProvider(day, month, year)
        }
        with(binding) {
            CreateProjectButton.setOnClickListener {
                if (enterDescriptionTextInputLayout.text.toString()
                        .isNotEmpty() && enterTaskNameInputEditText.text.toString().isNotEmpty()
                ) {
                    observeData(viewModel.endDate) {
                        val df = SimpleDateFormat(DATE_FORMATTER, Locale.getDefault())
                        if (viewModel.dataValidator(df.format(Calendar.getInstance().time), it)) {
                            viewModel.createProject(
                                enterTaskNameInputEditText.text.toString(),
                                enterDescriptionTextInputLayout.text.toString(),
                                df.format(Calendar.getInstance().time),
                                it
                            )
                            with(root) {
                                startActionAfterAnimation(R.anim.fade_out_faster) {
                                    findNavController().navigate(R.id.action_newProjectFragment_to_homeFragment)
                                }
                                isClickable = false
                            }
                        } else {
                            getString(R.string.date_picker_error).toast(requireContext())
                        }
                    }

                } else {
                    getString(R.string.fill_error).toast(requireContext())
                }

            }
        }
    }

    private fun setAnimation() {
        with(binding) {
            imageView3.animation(R.anim.bottomanimation)
            taskTitle.animation(R.anim.bottomanimation)
            enterDescriptionEditText.animation(R.anim.bottomanimation)
            enterTaskNameTextInputLayout.animation(R.anim.bottomanimation)
            datePicker.animation(R.anim.top_animation)
            CreateProjectButton.animation(R.anim.top_animation)
        }
    }
}


