package com.ueno.todolistapp.datasource

import com.ueno.todolistapp.domain.Task

object TaskDataSource {

    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }
    }

    fun deleteTask(task: Task) {
        list.remove(task)
    }

}