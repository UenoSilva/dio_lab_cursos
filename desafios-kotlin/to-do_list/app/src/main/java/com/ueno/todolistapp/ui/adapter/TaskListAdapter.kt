package com.ueno.todolistapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ueno.todolistapp.R
import com.ueno.todolistapp.databinding.TaskItemBinding
import com.ueno.todolistapp.domain.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    private var listenerEdit: (Task) -> Unit = {}
    private var listenerDelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(inflate, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class TaskViewHolder(private val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.tvTitleTask.text = item.title
            binding.tvDate.text = "${item.date} ${item.hour}"
            binding.ivMore.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            var ivMore = binding.ivMore
            var popMenu = PopupMenu(ivMore.context, ivMore)
            popMenu.menuInflater.inflate(R.menu.popup_menu, popMenu.menu)
            popMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_edit -> listenerEdit(item)
                    R.id.menu_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popMenu.show()
        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id
}