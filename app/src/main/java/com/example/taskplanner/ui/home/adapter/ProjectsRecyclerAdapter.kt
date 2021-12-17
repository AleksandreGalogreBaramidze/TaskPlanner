package com.example.taskplanner.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskplanner.databinding.ProjectsItemBinding
import com.example.taskplanner.models.ProjectModel


class ProjectsRecyclerAdapter : RecyclerView.Adapter<ProjectsRecyclerAdapter.ProjectViewHolder>() {
    private var data = mutableListOf<ProjectModel>()
    lateinit var getItemUid : (String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(
            ProjectsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    //TODO daxatva wavigho viewholderklasshi
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val item = data[position]
        with(holder.binding){
            nameTextView.text = item.projectName
            progressTextView.text = item.progress
            openButton.setOnClickListener {
                getItemUid.invoke(item.projectUid)
            }
        }
    }

    override fun getItemCount() = data.size

    class ProjectViewHolder(val binding: ProjectsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    //TODO difutili gamovikeno
    fun setData(data: List<ProjectModel>) {
        this.data.clear()
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

}