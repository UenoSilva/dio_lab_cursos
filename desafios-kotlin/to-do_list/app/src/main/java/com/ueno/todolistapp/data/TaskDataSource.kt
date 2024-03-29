package com.ueno.todolistapp.data

import com.ueno.todolistapp.data.local.TaskRepository
import com.ueno.todolistapp.domain.Task

object TaskDataSource {

    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task) {
        if (task.id == 0L) {
            list.add(task.copy(id = list.size + 1L))
        } else {
            list.remove(task)
            list.add(task)
        }
    }

    fun findTaskById(taskId: Long) = list.find { it.id == taskId }

    fun deleteTask(task: Task) {
        list.remove(task)
    }

}