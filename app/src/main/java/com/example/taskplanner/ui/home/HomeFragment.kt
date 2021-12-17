package com.example.taskplanner.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskplanner.R
import com.example.taskplanner.databinding.HomeFragmentBinding
import com.example.taskplanner.extensions.*
import com.example.taskplanner.ui.basefragment.BaseFragment
import com.example.taskplanner.ui.home.adapter.ProjectsRecyclerAdapter
import com.example.taskplanner.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> HomeFragmentBinding get() = HomeFragmentBinding::inflate
    override fun getViewModel() = HomeViewModel::class.java
    private lateinit var homeRecyclerAdapter: ProjectsRecyclerAdapter
    override fun initFragment(viewModel: HomeViewModel) {
        viewModel.getCurrentUser()
        viewModel.getTasks()
        viewModel.getProgressStatistics()
        setListeners(viewModel)
        startAnimation()
        observeData(viewModel.progressLiveData) {
            with(binding) {
                doneNumberTextView.text = it.done.toString()
                workInProgressNumberTextView.text = it.inProgress.toString()
                toDoNumberTextView.text = it.todo.toString()
            }
        }

        observeData(viewModel.user) {
            when (it) {
                is Resource.Success -> {
                    with(binding) {
                        ProfilePictureImageView.getImageFromUrl(it.data!!.profileImageUrl)
                        userNameTextView.text = it.data.userName
                        homeFragmentProgressBar.isVisible = false
                        binding.userWorkTextView.text = it.data.job
                        binding.userWorkEditText.setText(it.data.job)
                    }
                }
                is Resource.Loading -> {
                    binding.homeFragmentProgressBar.isVisible = true
                }
                is Resource.Error -> {
                    binding.homeFragmentProgressBar.isVisible = false
                    it.errorMessage?.toast(requireContext())
                }
            }
        }

        observeData(viewModel.optionLiveData) {
            initRecycler()
            homeRecyclerAdapter.setData(it)
        }
        binding.addTaskButton.setOnClickListener {
            with(binding.root) {
                startActionAfterAnimation(R.anim.fade_out_faster) {
                    findNavController().navigate(R.id.action_homeFragment_to_newProjectFragment)
                }
                binding.root.isClickable = false
            }
        }

    }

    private fun initRecycler() {
        with(binding.tasksRecyclerView) {
            homeRecyclerAdapter = ProjectsRecyclerAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRecyclerAdapter
        }

        homeRecyclerAdapter.getItemUid = {
            with(binding.root) {
                startActionAfterAnimation(R.anim.fade_out_faster) {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToProjectFragment(
                            it
                        )
                    )
                }
                isClickable = false
            }
        }
    }

    private fun setListeners(viewModel: HomeViewModel) {
        with(binding) {
            userWorkTextView.setOnClickListener {
                userWorkTextView.isVisible = false
                userWorkEditText.isVisible = true
                updateUserNameButton.isVisible = true
                updateUserNameButton.animation(R.anim.fade_in)
            }
            binding.updateUserNameButton.setOnClickListener {
                viewModel.updateJob(userWorkEditText.text.toString())
                userWorkTextView.isVisible = true
                userWorkTextView.animation(R.anim.fade_in)
                userWorkEditText.isVisible = false
                updateUserNameButton.isVisible = false
                userWorkTextView.text = userWorkEditText.text
            }
            logOutImageView.setOnClickListener {
                viewModel.signOutUser()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            }
        }
    }

    //TODO MOTIONZE gadavitano
    private fun startAnimation() {
        with(binding) {
            blueBackGround.animation(R.anim.bottomanimation)
            ProfilePictureImageView.animation(R.anim.bottomanimation)
            userNameTextView.animation(R.anim.bottomanimation)
            userWorkTextView.animation(R.anim.bottomanimation)
            currentTasksTextView.animation(R.anim.bottomanimation)
            cardView2.animation(R.anim.bottomanimation)
            cardView.animation(R.anim.top_animation)
            addTaskButton.animation(R.anim.top_animation)
        }
    }
}