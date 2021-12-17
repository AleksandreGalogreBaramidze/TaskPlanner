package com.example.taskplanner.ui.project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskplanner.R
import com.example.taskplanner.databinding.ProjectFragmentBinding
import com.example.taskplanner.extensions.animation
import com.example.taskplanner.extensions.observeData
import com.example.taskplanner.extensions.startActionAfterAnimation
import com.example.taskplanner.extensions.toast
import com.example.taskplanner.ui.basefragment.BaseFragment
import com.example.taskplanner.ui.project.adapter.TasksRecyclerAdapter
import com.example.taskplanner.utils.Constants.DATE_FORMATTER
import com.example.taskplanner.utils.Constants.DONE
import com.example.taskplanner.utils.Constants.IN_PROGRESS
import com.example.taskplanner.utils.Constants.TODO_PROGRESS
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProjectFragment : BaseFragment<ProjectFragmentBinding, ProjectViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ProjectFragmentBinding get() = ProjectFragmentBinding::inflate
    override fun getViewModel() = ProjectViewModel::class.java
    private lateinit var subTasksRecyclerAdapter: TasksRecyclerAdapter

    private val args: ProjectFragmentArgs by navArgs()

    override fun initFragment(viewModel: ProjectViewModel) {
        setAnimations()
        initRecycler()
        viewModel.getTasks(args.id)
        viewModel.getProjectDetails(args.id)
        setChipStatus(viewModel)
        observeData(viewModel.projectLiveData) {
            with(binding) {
                projectNameTextView.text = it.projectName
                projectDescriptionTextView.text = it.description
                startDateTextView.text = it.startDate
                endDateTexTView.text = it.endDate
                projectNameEditText.setText(it.projectName)
                projectDescriptionEditText.setText(it.description)
                when (it.progress) {
                    TODO_PROGRESS -> todoChip.isChecked = true
                    IN_PROGRESS -> inProgressChip.isChecked = true
                    DONE -> doneChip.isChecked = true
                }
            }
        }
        observeData(viewModel.tasksListLiveData) {
            subTasksRecyclerAdapter.setData(it)
        }
        setListeners(viewModel)
        val today = Calendar.getInstance()
        binding.projectDatePicker.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { _, year, month, day ->
            viewModel.dataProvider(day, month, year)
        }

    }

    private fun setChipStatus(viewModel: ProjectViewModel) {
        binding.progressChipGroup.setOnCheckedChangeListener { _, checkedId ->
            with(viewModel) {
                when (checkedId) {
                    R.id.todoChip -> {
                        updateProgress(args.id, TODO_PROGRESS)
                    }
                    R.id.inProgressChip -> {
                        updateProgress(args.id, IN_PROGRESS)
                    }
                    R.id.doneChip -> {
                        updateProgress(args.id, DONE)

                    }
                }
            }
        }
    }

    private fun initRecycler() {
        with(binding.tasksRecyclerView) {
            subTasksRecyclerAdapter = TasksRecyclerAdapter()
            //TODO shemidzlia xml gadavawodo manager
            layoutManager = LinearLayoutManager(requireContext())
            adapter = subTasksRecyclerAdapter
        }
        subTasksRecyclerAdapter.getItemId = {
            with(binding.root) {
                startActionAfterAnimation(R.anim.fade_out_faster) {
                    findNavController().navigate(
                        ProjectFragmentDirections.actionProjectFragmentToTaskDetailFragment(
                            it
                        )
                    )
                }
                isClickable = false
            }
        }
    }

    private fun setListeners(viewModel: ProjectViewModel) {
        with(binding) {
            deleteProjectImageView.setOnClickListener {
                viewModel.deleteProjectById(args.id)
                with(root) {
                    startActionAfterAnimation(R.anim.fade_out_faster) {
                        findNavController().navigate(R.id.action_projectFragment_to_homeFragment)
                    }
                    isClickable = false
                }
            }

            createTaskButton.setOnClickListener {
                if (subTaskNameTextInputLayout.isGone) {
                    tasksRecyclerCardView.isGone = true
                    subTaskNameTextInputLayout.isGone = false
                    projectDatePicker.isGone = false
                    projectDatePicker.animation(R.anim.fade_in)
                    subTaskNameTextInputLayout.animation(R.anim.fade_in)
                } else {
                    //TODO calendaris obieqtidan gavaketo validatori
                    if (subTaskEditText.text.toString().isNotEmpty()) {
                        observeData(viewModel.endDate) {
                            val df = SimpleDateFormat(DATE_FORMATTER, Locale.getDefault())
                            if (viewModel.dataValidator(
                                    df.format(Calendar.getInstance().time),
                                    it
                                )
                            ) {
                                viewModel.createTask(
                                    subTaskEditText.text.toString(),
                                    args.id,
                                    df.format(Calendar.getInstance().time),
                                    it
                                )
                            } else {
                                getString(R.string.date_picker_error).toast(requireContext())
                            }
                        }
                        tasksRecyclerCardView.isGone = false
                        tasksRecyclerCardView.animation(R.anim.fade_in)
                        subTaskNameTextInputLayout.isGone = true
                        projectDatePicker.isGone = true
                        viewModel.getTasks(args.id)
                    } else {
                        getString(R.string.fill_error).toast(requireContext())
                    }

                }
            }
            editButton.setOnClickListener {
                projectNameEditText.isVisible = true
                projectDescriptionEditText.isVisible = true
                projectNameTextView.isVisible = false
                projectDescriptionTextView.isVisible = false
                editButton.isGone = true
                createSubTask.isGone = false
            }
            createSubTask.setOnClickListener {
                projectNameEditText.isVisible = false
                projectDescriptionEditText.isVisible = false
                projectNameTextView.isVisible = true
                projectDescriptionTextView.isVisible = true
                editButton.isGone = false
                createSubTask.isGone = true
                viewModel.updateProject(
                    args.id,
                    projectNameEditText.text.toString(),
                    projectDescriptionEditText.text.toString()
                )
                viewModel.getProjectDetails(args.id)
            }
        }
    }

    private fun setAnimations() {
        with(binding) {
            deleteProjectImageView.animation(R.anim.bottomanimation)
            imageView4.animation(R.anim.bottomanimation)
            projectNameTextView.animation(R.anim.bottomanimation)
            editButton.animation(R.anim.bottomanimation)
            taskTitle3.animation(R.anim.bottomanimation)
            cardView2.animation(R.anim.bottomanimation)
            startDateTextView.animation(R.anim.bottomanimation)
            startDateTextView2.animation(R.anim.bottomanimation)
            startDateTextView3.animation(R.anim.bottomanimation)
            endDateTexTView.animation(R.anim.bottomanimation)
            progressChipGroup.animation(R.anim.bottomanimation)
            taskTitle4.animation(R.anim.bottomanimation)
            tasksRecyclerCardView.animation(R.anim.top_animation)
            createTaskButton.animation(R.anim.top_animation)
        }
    }
}