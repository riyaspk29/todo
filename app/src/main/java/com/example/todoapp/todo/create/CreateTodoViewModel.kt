package com.example.todoapp.todo.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.ToDoDatabaseDao
import com.example.todoapp.database.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateTodoViewModel(
        private val editTodoId: Long,
        val database: ToDoDatabaseDao,
        application: Application
) : AndroidViewModel(application) {

    private val _addingCompleted = MutableLiveData<Boolean>()
    val addingCompleted: LiveData<Boolean>
        get() = _addingCompleted

    private val _todoEditObj = MutableLiveData<Todo>()
    val todoEditObj: LiveData<Todo>
        get() = _todoEditObj

    var taskEditTextData = MutableLiveData<String>()

    init {
        getTodoObj()
    }

    private fun getTodoObj(){
        viewModelScope.launch {
            _todoEditObj.value = database.get(editTodoId)
            _todoEditObj.value?.task.let {
                taskEditTextData.value = it
            }
        }
    }

    private suspend fun insert(todo: Todo) {
        withContext(Dispatchers.IO) {
            database.insert(todo)
        }
    }

//    private suspend fun update(todo: Todo) {
//        withContext(Dispatchers.IO) {
//            database.update(todo)
//        }
//    }

    fun addButtonTap() {
        taskEditTextData.value?.let { taskStr ->
            if (editTodoId == 0L) {
                val todo = Todo(task = taskStr)
                addTodo(todo)
            } else {
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        _todoEditObj.value?.let {
                            it.task = taskStr
                            database.update(it)
                        }
                    }
                }
            }
            _addingCompleted.value = true
        }
    }

    private fun addTodo(todo: Todo) {
        viewModelScope.launch {
            insert(todo)
        }
    }
}