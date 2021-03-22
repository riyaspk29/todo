package com.example.todoapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list_table")
data class Todo(

        @PrimaryKey(autoGenerate = true)
        var todoId: Long = 0L,

        @ColumnInfo(name = "task")
        var task: String = "",

        @ColumnInfo(name = "status")
        var status: Boolean = false,

        @ColumnInfo(name = "time_milli")
        var endTimeMilli: Long = System.currentTimeMillis(),
)
