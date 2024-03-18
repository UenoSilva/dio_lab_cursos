package com.ueno.todolistapp.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.util.Log
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_DATE
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_DESCRIPTION
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_HOUR
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_ID
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_TITLE
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.TABLE_NAME
import com.ueno.todolistapp.domain.Task

class TaskRepository(private val context: Context) {
    fun save(task: Task) {
        try {
            val dbHelper = TaskDbHelper(context)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(COLUMNS_NAME_ID, task.id)
                put(COLUMNS_NAME_TITLE, task.title)
                put(COLUMNS_NAME_DESCRIPTION, task.description)
                put(COLUMNS_NAME_DATE, task.date)
                put(COLUMNS_NAME_HOUR, task.hour)
            }
            val insert = db?.insert(TABLE_NAME, null, values)
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    fun findTaskBYId(id: Int): Task {
        val dbHelper = TaskDbHelper(context)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMNS_NAME_ID,
            COLUMNS_NAME_TITLE,
            COLUMNS_NAME_DESCRIPTION,
            COLUMNS_NAME_DATE,
            COLUMNS_NAME_HOUR
        )

        val filter = "$COLUMNS_NAME_ID = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            TaskContract.TaskEntry.TABLE_NAME, columns, filter, filterValues, null, null, null
        )

        var itemTask = Task(
            id = 0, title = "", description = "", date = "", hour = ""
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMNS_NAME_ID))
                val title = getString(getColumnIndexOrThrow(COLUMNS_NAME_TITLE))
                val description = getString(getColumnIndexOrThrow(COLUMNS_NAME_DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(COLUMNS_NAME_DATE))
                val hour = getString(getColumnIndexOrThrow(COLUMNS_NAME_HOUR))

                itemTask = Task(
                    id = id, title = title, description = description, date = date, hour = hour
                )
            }
        }
        cursor.close()
        return itemTask
    }

    fun getAll(): MutableList<Task> {
        val dbHelper = TaskDbHelper(context)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMNS_NAME_ID,
            COLUMNS_NAME_TITLE,
            COLUMNS_NAME_DESCRIPTION,
            COLUMNS_NAME_DATE,
            COLUMNS_NAME_HOUR
        )

        val cursor = db.query(
            TaskContract.TaskEntry.TABLE_NAME, columns, null, null, null, null, null
        )
        val taskList = mutableListOf<Task>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMNS_NAME_ID))
                val title = getString(getColumnIndexOrThrow(COLUMNS_NAME_TITLE))
                val description = getString(getColumnIndexOrThrow(COLUMNS_NAME_DESCRIPTION))
                val date = getString(getColumnIndexOrThrow(COLUMNS_NAME_DATE))
                val hour = getString(getColumnIndexOrThrow(COLUMNS_NAME_HOUR))

                taskList.add(
                    Task(
                        id = id, title = title, description = description, date = date, hour = hour
                    )
                )
            }
        }
        cursor.close()
        return taskList
    }

    fun delete(task: Task) {
        val dbHelper = TaskDbHelper(context)
        val db = dbHelper.readableDatabase
        val filter = "$COLUMNS_NAME_ID = ?"
        val filterValues = arrayOf(task.id.toString())
        val deletedRows = db.delete(TABLE_NAME, filter, filterValues)
    }

    fun savaIfNotExist(task: Task) {
        var taskId = findTaskBYId(task.id)
        if (taskId.id == 0) {
            save(task)
        }
    }

}




