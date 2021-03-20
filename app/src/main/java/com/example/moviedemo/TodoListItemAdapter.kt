package com.example.moviedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoListItemAdapter(val list:List<TodoItem>): RecyclerView.Adapter<TodoListItemAdapter.TodoItemViewHolder>() {

    class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = list[position]
        holder.itemView.titleText.text = todoItem.title
    }

    override fun getItemCount(): Int {
        return list.size
    }

}