package com.example.todoapp.todo.create

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.database.ToDoDatabaseDao

class CreateTodoViewModelFactory(
        private val editId: Long,
        private val dataSource: ToDoDatabaseDao,
        private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateTodoViewModel::class.java)) {
            return CreateTodoViewModel(editId, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}