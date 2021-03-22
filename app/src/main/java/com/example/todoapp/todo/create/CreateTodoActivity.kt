package com.example.todoapp.todo.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.databinding.ActivityCreateTodoBinding

class CreateTodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCreateTodoBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_create_todo
        )
        val todoId = intent.getLongExtra("todoId", 0L)
        title = getString(R.string.add_a_task)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val dataSource = ToDoDatabase.getInstance(application).toDoDatabaseDao
        val viewModelFactory = CreateTodoViewModelFactory(todoId, dataSource, application)
        val createTodoViewModel = ViewModelProvider(this, viewModelFactory).get(CreateTodoViewModel::class.java)

        binding.createTodoViewModel = createTodoViewModel
        binding.lifecycleOwner = this

        createTodoViewModel.todoEditObj.observe(this, {
            it?.let {
                title = getString(R.string.edit_task)
            }
        })

        createTodoViewModel.addingCompleted.observe(this, {
            it?.let {
                finish()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}