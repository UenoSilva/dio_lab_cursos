package com.ueno.todolistapp.data.local

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_DATE
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_DESCRIPTION
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_HOUR
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_ID
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.COLUMNS_NAME_TITLE
import com.ueno.todolistapp.data.local.TaskContract.TaskEntry.TABLE_NAME
import com.ueno.todolistapp.domain.Task

@Suppress("NAME_SHADOWING")
class TaskRepository(private val context: Context) {
    private fun save(title: String, description: String, date: String, hour: String) {

        val dbHelper = TaskDbHelper(context)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMNS_NAME_TITLE, title)
            put(COLUMNS_NAME_DESCRIPTION, description)
            put(COLUMNS_NAME_DATE, date)
            put(COLUMNS_NAME_HOUR, hour)
        }
        val insert = db?.insert(TABLE_NAME, null, values)

        Log.e("SAVE", "$insert")

    }

    fun findTaskBYId(id: String): Task {
        val dbHelper = TaskDbHelper(context)
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            COLUMNS_NAME_ID,
            COLUMNS_NAME_TITLE,
            COLUMNS_NAME_DESCRIPTION,
            COLUMNS_NAME_DATE,
            COLUMNS_NAME_HOUR
        )
        val filterValues = arrayOf(id)
        val cursor = db.query(
            TABLE_NAME,
            columns,
            "task_id = ?",
            filterValues,
            null,
            null,
            null
        )

        var itemTask = Task(
            id = 0, title = "", description = "", date = "", hour = ""
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMNS_NAME_ID))
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
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val taskList = mutableListOf<Task>()

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMNS_NAME_ID))
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

    fun delete(id: String) {
        val dbHelper = TaskDbHelper(context)
        val db = dbHelper.writableDatabase
        db.delete(TABLE_NAME, "task_id = ?", arrayOf(id))
    }

    private fun update(id: String, title: String, description: String, date: String, hour: String) {
        val dbHelper = TaskDbHelper(context)
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMNS_NAME_TITLE, title)
            put(COLUMNS_NAME_DESCRIPTION, description)
            put(COLUMNS_NAME_DATE, date)
            put(COLUMNS_NAME_HOUR, hour)
        }
        db.update(TABLE_NAME, values, "task_id = ?", arrayOf(id))
    }

    fun savaIfNotExist(task: Task) {
        //var taskId = findTaskBYId(task.id)
        val taskId = findTaskBYId(task.id.toString())
        Log.e("TASK ID", "$taskId")
        if (taskId.id == 0L) {
            save(task.title, task.description, task.date, task.hour)
            Log.e("SAVE TASK", "a task foi salvar")
        } else {
            Log.e("UPADATE TASK", "a task foi atualizou")
            update(task.id.toString(), task.title, task.description, task.date, task.hour)
        }
    }

}




