package com.project.todaylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.todaylist.databinding.RecyclerTodoBinding

class TodoAdapter(var todoList: MutableList<TodoMain.TodoList>,
                  private val selectedTodo:OnClickListener,
                  private val deleteCallback: AdapterCallback) :RecyclerView.Adapter<TodoAdapter.TodoAdapterViewHolder>(){

    var clickedPosion = RecyclerView.NO_POSITION
    interface OnClickListener{
        fun onItemClice(position: Int)
    }

    interface AdapterCallback {
        fun onTodoDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapterViewHolder =
        TodoAdapterViewHolder(RecyclerTodoBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoAdapterViewHolder, position: Int) {
        val todoList = todoList[position]
        holder.bind(todoList)
    }

    inner class TodoAdapterViewHolder(val recyclerBinding : RecyclerTodoBinding):RecyclerView.ViewHolder(recyclerBinding.root){
        fun bind(todoList: TodoMain.TodoList){
            recyclerBinding.recyclerContents.text = todoList.name

            recyclerBinding.root.setOnClickListener {
                clickedPosion = adapterPosition
                selectedTodo.onItemClice(clickedPosion)
                notifyItemChanged(clickedPosion)
            }
            recyclerBinding.recyclerDelete.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION){
                    deleteCallback.onTodoDelete(adapterPosition)
                }
            }
        }


    }
}