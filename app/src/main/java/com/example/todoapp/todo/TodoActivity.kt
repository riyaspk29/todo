package com.example.todoapp.todo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.databinding.ActivityTodoBinding
import com.example.todoapp.todo.create.CreateTodoActivity

class TodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTodoBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_todo
        )


        val dataSource = ToDoDatabase.getInstance(application).toDoDatabaseDao
        val viewModelFactory = TodoViewModelFactory(dataSource, application)
        val todoViewModel = ViewModelProvider(this, viewModelFactory).get(TodoViewModel::class.java)

        binding.todoViewModel = todoViewModel


        val adapter = TodoAdapter(TodoListener { todoId, buttonTypes ->
            todoViewModel.itemButtonClicks(todoId, buttonTypes)
        })
        binding.todoList.adapter = adapter

        binding.lifecycleOwner = this

        todoViewModel.allTodo.observe(this, {
            it?.let {
                adapter.data = it
            }
        })

        todoViewModel.navigateToCreate.observe(this, { navigateToCreate ->
            navigateToCreate?.let {
                if(it){
                    val intent = Intent(this, CreateTodoActivity::class.java)
                    startActivity(intent)
                    todoViewModel.navigationFinished()
                }
            }
        })

        todoViewModel.editId.observe(this, { navigateToCreate ->
            navigateToCreate?.let {
                val intent = Intent(this, CreateTodoActivity::class.java)
                intent.putExtra("todoId",it)
                startActivity(intent)
                todoViewModel.navigationFinished()
            }
        })
    }
}