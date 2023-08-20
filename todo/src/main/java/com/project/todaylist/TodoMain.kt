package com.project.todaylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.todaylist.databinding.ActivityTodoMainBinding
import com.project.todaylist.databinding.RecyclerTodoBinding
import java.io.File
import java.io.FileWriter
import kotlin.Exception

class TodoMain : AppCompatActivity(),TodoAdapter.OnClickListener {
    lateinit var todoMainBinding: ActivityTodoMainBinding
    lateinit var todoAdapter: TodoAdapter
    lateinit var todoRecycler: RecyclerTodoBinding
    lateinit var fileName: String

    var selectedIndex:Int? = null
    data class TodoList(var name: String)
    var todoList:MutableList<TodoList> = mutableListOf()

    val adapterCallback = object : TodoAdapter.AdapterCallback {
        override fun onTodoDelete(position: Int) {
            todoList.removeAt(position)
            todoDelete(position)
            todoAdapter.notifyItemRemoved(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoMainBinding = ActivityTodoMainBinding.inflate(layoutInflater)
        setContentView(todoMainBinding.root)

        todoRecycler = RecyclerTodoBinding.inflate(layoutInflater)

        todoAdapter = TodoAdapter(todoList, this,adapterCallback)
        todoMainBinding.recyclerTodo.adapter = todoAdapter
        todoMainBinding.recyclerTodo.layoutManager = LinearLayoutManager(this)

        val year = intent.getIntExtra("year", 0)
        val month = intent.getIntExtra("month",0)
        val day = intent.getIntExtra("dayOfMonth",0)

        todoMainBinding.todoDiary.text = String.format("%d / %d / %d", year, month+1, day)
        todoMainBinding.todoEditContent.setText("")
        todoLoad(year, month, day)

        todoMainBinding.todoAdd.setOnClickListener {
            if(todoMainBinding.todoEditContent.text.isNotEmpty()){
                val contents = todoMainBinding.todoEditContent.text.toString()
                val contentList = TodoList(contents)
                Log.w("zio","saveContentAdd: $contents")
                Log.w("zio","saveContentFname: $fileName")
                todoList.add(contentList)
                todoMainBinding.todoEditContent.setText("")
                todoAdapter.notifyItemInserted(todoList.size-1)
                todoSave(contents)
                Log.w("zio","contentList: $contentList")
            }
        }

        todoMainBinding.todoUpdate.setOnClickListener {
            val updateContent = todoMainBinding.todoEditContent.text.toString()

            if(selectedIndex != null && updateContent.isNotEmpty()){
                todoList[selectedIndex!!] = TodoList(updateContent)
                todoAdapter.notifyItemChanged(selectedIndex!!)
                todoMainBinding.todoEditContent.setText("")
                todoSave(updateContent,selectedIndex)
                todoMainBinding.todoUpdate.visibility = View.INVISIBLE
                todoMainBinding.todoAdd.visibility = View.VISIBLE
                selectedIndex = null
            }
        }

        todoMainBinding.todoHome.setOnClickListener {
            intent = Intent(this, TodoTitle::class.java)
            startActivity(intent)
        }

        val filePath = filesDir.absolutePath
        Log.w("zio","파일위치 : $filePath")

    }

    fun todoLoad(cYear: Int, cMonth:Int, cDay: Int){
        try {
            fileName = ""+ cYear +"-"+ (cMonth+1)+"-"+ cDay +".txt"
            val todoFile = File(filesDir, fileName)
            if(todoFile.exists()){
                val fileText = todoFile.readText()
                if(fileText.isNotEmpty()){
                    todoList.clear()
                    fileText.split("\n").forEach{line ->
                        if(line.isNotEmpty()){
                            todoList.add(TodoList(line))
                        }
                    }
                    todoAdapter.notifyDataSetChanged()
                }
            }
        }catch (e: Exception){
            Log.w("zio","loadError: $e")
            e.printStackTrace()
        }
    }

    fun todoSave(content:String, index:Int? = null){
        try {
            val file = File(filesDir, fileName)
            val writer = FileWriter(file, false)
            writer.use { out ->
                if(index != null){
                    selectedIndex = index
                    todoList[index] = TodoList(content)
                }
                val updateList = todoList.joinToString(separator = "\n"){it.name}
                out.write(updateList)
            }
        }catch (e:Exception){
            Log.w("zio","saveError: $e")
            e.printStackTrace()
        }
    }

    fun todoDelete(position: Int){
        try {
            val file = File(filesDir, fileName)
            val writer = FileWriter(file, false)
            writer.use { out ->
                val updateList = todoList.joinToString(separator = "\n"){it.name}
                out.write(updateList)
                Log.w("zio","updateList: $updateList")
            }
        }catch (e:Exception){
            Log.w("zio","deleteError: $e")
            e.printStackTrace()
        }
    }

    override fun onItemClice(position: Int) {
        selectedIndex = position
        val todoListItem = todoList[position]
        todoMainBinding.todoEditContent.setText(todoListItem.name)
        todoMainBinding.todoUpdate.visibility = View.VISIBLE
        todoMainBinding.todoAdd.visibility = View.INVISIBLE
    }
}


