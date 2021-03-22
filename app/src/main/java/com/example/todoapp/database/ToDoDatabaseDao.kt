package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ToDoDatabaseDao {

    @Insert
    suspend fun insert(night: Todo)

    @Update
    suspend fun update(night: Todo)

    @Query("SELECT * from todo_list_table WHERE todoId = :key")
    suspend fun get(key: Long): Todo?

    @Query("DELETE FROM todo_list_table")
    suspend fun clear()

    @Query("SELECT * FROM todo_list_table ORDER BY todoId DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    @Query("DELETE from todo_list_table WHERE todoId = :key")
    suspend fun delete(key: Long)
}

