package com.example.taskplanner.ui.task_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskplanner.R
import com.example.taskplanner.databinding.TaskDetailFragmentBinding
import com.example.taskplanner.extensions.animation
import com.example.taskplanner.extensions.observeData
import com.example.taskplanner.extensions.startActionAfterAnimation
import com.example.taskplanner.ui.basefragment.BaseFragment
import com.example.taskplanner.utils.Constants
import com.example.taskplanner.utils.Constants.FADE_OUT_TRANSACTION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TaskDetailFragment : BaseFragment<TaskDetailFragmentBinding, TaskDetailViewModel>() {
    override fun getViewModel() = TaskDetailViewModel::class.java
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> TaskDetailFragmentBinding get() = TaskDetailFragmentBinding::inflate
    private val args: TaskDetailFragmentArgs by navArgs()
    override fun initFragment(viewModel: TaskDetailViewModel) {
        setAnimations()
        viewModel.getTasksDetails(args.id)
        setChipStatus(viewModel)
        setListeners(viewModel)

        observeData(viewModel.taskDetails) {
            with(binding) {
                projectNameTextView.text = it.taskName
                startDateTextView.text = it.startDate
                endDateTexTView.text = it.endDate
                projectNameEditText.setText(it.taskName)
                when (it.progress) {
                    Constants.TODO_PROGRESS -> binding.todoChip.isChecked = true
                    Constants.IN_PROGRESS -> binding.inProgressChip.isChecked = true
                    Constants.DONE -> binding.doneChip.isChecked = true
                }
            }
        }
    }
    //TODO visibility extension
    private fun setChipStatus(viewModel: TaskDetailViewModel) {
        binding.progressChipGroup.setOnCheckedChangeListener { _, checkedId ->
            with(viewModel) {
                when (checkedId) {
                    R.id.todoChip -> {
                        updateProgress(args.id, Constants.TODO_PROGRESS)
                    }
                    R.id.inProgressChip -> {
                        updateProgress(args.id, Constants.IN_PROGRESS)
                    }
                    R.id.doneChip -> {
                        updateProgress(args.id, Constants.DONE)
                    }
                }
            }
        }
    }


    private fun setListeners(viewModel: TaskDetailViewModel) {
        with(binding) {
            editButton.setOnClickListener {
                projectNameEditText.isVisible = true
                projectNameTextView.isVisible = false
                editButton.isGone = true
                createSubTask.isGone = false
            }

            imageView5.setOnClickListener {
                viewModel.deleteTaskById(args.id)
                with(binding.root) {
                    startActionAfterAnimation(R.anim.fade_out_faster) {
                        findNavController().navigate(R.id.action_taskDetailFragment_to_projectFragment)
                    }
                    binding.root.isClickable = false
                }
            }

            createSubTask.setOnClickListener {
                projectNameEditText.isVisible = false
                projectNameTextView.isVisible = true
                editButton.isGone = false
                createSubTask.isGone = true
                viewModel.updateTask(args.id, projectNameEditText.text.toString())
                viewModel.getTasksDetails(args.id)
            }
        }
    }

    private fun setAnimations() {
        with(binding) {
            imageView4.animation(R.anim.bottomanimation)
            imageView5.animation(R.anim.bottomanimation)
            editButton.animation(R.anim.bottomanimation)
            projectNameTextView.animation(R.anim.bottomanimation)
            startDateTextView2.animation(R.anim.bottomanimation)
            startDateTextView.animation(R.anim.bottomanimation)
            startDateTextView3.animation(R.anim.bottomanimation)
            endDateTexTView.animation(R.anim.bottomanimation)
            progressChipGroup.animation(R.anim.bottomanimation)
        }
    }

}