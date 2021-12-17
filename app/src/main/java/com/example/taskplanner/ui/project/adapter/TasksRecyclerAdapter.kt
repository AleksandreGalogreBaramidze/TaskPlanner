package com.example.taskplanner.ui.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.databinding.SubtaskItemBinding
import com.example.taskplanner.models.TaskModel


class TasksRecyclerAdapter : RecyclerView.Adapter<TasksRecyclerAdapter.ProjectViewHolder>() {
    private var data = mutableListOf<TaskModel>()
    lateinit var getItemId : (String) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(
            SubtaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item = data[position]
        with(holder.binding){
            nameTextView.text = item.taskName
            progressTextView.text = item.progress
            root.setOnClickListener {
                getItemId.invoke(item.taskId!!)
            }
        }
    }

    override fun getItemCount() = data.size

    class ProjectViewHolder(val binding: SubtaskItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<TaskModel>) {
        this.data.clear()
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

}