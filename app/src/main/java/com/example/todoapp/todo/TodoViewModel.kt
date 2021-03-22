package com.example.todoapp.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.ToDoDatabaseDao
import com.example.todoapp.database.Todo

class TodoViewModel(
    val database: ToDoDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val _navigateToCreate = MutableLiveData<Boolean>()
    val navigateToCreate: LiveData<Boolean>
        get() = _navigateToCreate

    private val _editId = MutableLiveData<Long?>()
    val editId: LiveData<Long?>
        get() = _editId

    val allTodo = database.getAllTodo()

    init {
        _editId.value = null
    }

    fun goToCreate(){
        _navigateToCreate.value = true
    }

    fun navigationFinished() {
        _navigateToCreate.value = false
        _editId.value = null
    }

    fun itemButtonClicks(todoId: Long, buttonTypes: ButtonTypes){
        when (buttonTypes) {
            ButtonTypes.DELETE -> {
                deleteTodo(todoId)
            }
            ButtonTypes.DONE -> {
                changeStatusTodo(todoId)
            }
            ButtonTypes.EDIT -> {
                _editId.value = todoId
            }
        }
    }

    private fun deleteTodo(todoId: Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.delete(todoId)
            }
        }
    }

    private fun changeStatusTodo(todoId: Long){
        viewModelScope.launch {
            val todo = database.get(todoId)
            todo?.let {
                it.status = !it.status
                update(it)
            }
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(todo: Todo) {
        withContext(Dispatchers.IO) {
            database.update(todo)
        }
    }

//    private suspend fun insert(todo: Todo) {
//        withContext(Dispatchers.IO) {
//            database.insert(todo)
//        }
//    }

    fun onClear() {
        viewModelScope.launch {
            clear()
        }
    }
}
