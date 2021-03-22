package com.example.todoapp.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.database.Todo
import com.example.todoapp.databinding.TodoListItemBinding

class TodoAdapter(private val clickListener: TodoListener) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(){

    var data = listOf<Todo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(clickListener,item)
    }

    override fun getItemCount(): Int = data.size


    class ViewHolder private constructor(private val binding: TodoListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: TodoListener, item: Todo) {
            binding.todo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TodoListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }


}

class TodoListener(val clickListener: (todoId: Long, buttonTypes:ButtonTypes) -> Unit) {
    fun onClick(todo: Todo, buttonTypes:ButtonTypes) = clickListener(todo.todoId, buttonTypes)
}

enum class ButtonTypes {
    EDIT, DELETE, DONE
}