package com.ueno.todolistapp.data.local

import android.provider.BaseColumns

object TaskContract {
    object TaskEntry : BaseColumns {
        const val TABLE_NAME = "tb_task"
        const val COLUMNS_NAME_ID = "task_id"
        const val COLUMNS_NAME_TITLE = "title"
        const val COLUMNS_NAME_DESCRIPTION = "description"
        const val COLUMNS_NAME_DATE = "date"
        const val COLUMNS_NAME_HOUR = "hour"
    }

    const val TABLE_TASK =
        "CREATE TABLE ${TaskEntry.TABLE_NAME} (" +
                "${TaskEntry.COLUMNS_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${TaskEntry.COLUMNS_NAME_TITLE} TEXT," +
                "${TaskEntry.COLUMNS_NAME_DESCRIPTION} TEXT," +
                "${TaskEntry.COLUMNS_NAME_DATE} TEXT," +
                "${TaskEntry.COLUMNS_NAME_HOUR} TEXT )"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TaskEntry.TABLE_NAME}"
}