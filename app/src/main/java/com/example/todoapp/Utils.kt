package com.example.todoapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.todoapp.database.Todo

@BindingAdapter("statusImage")
fun ImageView.setStatusImage(item: Todo?) {
    item?.let {
        setImageResource(
            if (item.status) {
                R.drawable.ic_baseline_check_box_24
            }else {
                R.drawable.ic_baseline_check_box_outline_blank_24
            }
        )
    }
}