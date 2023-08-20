package com.project.todaylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.todaylist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var MainBinding: ActivityMainBinding
    private var backPressTime:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(MainBinding.root)

        MainBinding.todoMain.setOnClickListener {
            intent = Intent(this, TodoTitle::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - backPressTime < 2_000){
            finish()
        }else{
            Toast.makeText(this,"한버 더 누르면 앱이 종료됩니다.",Toast.LENGTH_SHORT).show()
            backPressTime = System.currentTimeMillis()
        }
    }
}