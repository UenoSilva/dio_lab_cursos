package com.ueno.todolistapp.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.ueno.todolistapp.R
import com.ueno.todolistapp.databinding.ActivityMainBinding
import com.ueno.todolistapp.datasource.TaskDataSource
import com.ueno.todolistapp.ui.adapter.TaskListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()
        setupListeners()
    }

    private fun setupListeners() {
        binding.fabCreateTask.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_TASK)
        }

        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_TASK && resultCode == Activity.RESULT_OK) {
            updateList()
        }
    }

    private fun updateList() {
        var list = TaskDataSource.getList()
        if (list.isEmpty()) {
            binding.includedEmptyState.clEmptyState.visibility = View.VISIBLE
        } else {
            binding.includedEmptyState.clEmptyState.visibility = View.GONE
            binding.rvTasks.visibility = View.VISIBLE
        }
        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_TASK = 1000
    }
}

