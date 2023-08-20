package com.project.todaylist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.project.todaylist.databinding.ActivityTodoTitleBinding
import java.lang.Exception

class TodoTitle : AppCompatActivity() {
    lateinit var todoTitleBiding : ActivityTodoTitleBinding
    private var backPressTime:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoTitleBiding = ActivityTodoTitleBinding.inflate(layoutInflater)
        setContentView(todoTitleBiding.root)

        todoTitleBiding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            intent = Intent(this, TodoMain::class.java)
            intent.putExtra("year",year)
            intent.putExtra("month",month)
            intent.putExtra("dayOfMonth",dayOfMonth)
            startActivity(intent)
        }

        todoTitleBiding.iconKakao.setOnClickListener {
            try {
                val kakao = packageManager.getLaunchIntentForPackage("com.kakao.talk")
                Log.w("zio","intentKakao1: $kakao")
                if(kakao != null){
                    Log.w("zio","intentKako2: $kakao")
                    ContextCompat.startActivity(it.context,kakao,null)
                } else {
                    Toast.makeText(this,"카카오톡을 설치하세요",Toast.LENGTH_SHORT).show()
                }
            }catch (e : Exception){
                Log.w("zio","error : $e")
            }
        }

        todoTitleBiding.iconInsta.setOnClickListener {
            try {
                val instagram = this.packageManager.getLaunchIntentForPackage("com.instagram.android")
                Log.w("zio","intentInstagram1: $instagram")
                if(instagram != null){
                    ContextCompat.startActivity(it.context,instagram,null)
                    Log.w("zio","intentInstagram2: $instagram")
                } else {
                    Toast.makeText(this,"인스타그램을 설치하세요",Toast.LENGTH_SHORT).show()
                }
            }catch (e : Exception){
                Log.w("zio","error : $e")
            }
        }

        todoTitleBiding.iconUtube.setOnClickListener {
            try {
                val utube = packageManager.getLaunchIntentForPackage("com.google.android.youtube")
                Log.w("zio","intentUtube1: $utube")
                if(utube != null){
                    startActivity(utube)
                    Log.w("zio","intentUtube2: $utube")
                } else {
                    Toast.makeText(this,"install YouTube",Toast.LENGTH_SHORT).show()
                }
            } catch (e:Exception){
                Log.w("zio","error: $e")
            }
        }

        todoTitleBiding.iconLine.setOnClickListener {
            try {
                val line = this.packageManager.getLaunchIntentForPackage("jp.naver.line.android")
                Log.w("zio","intentLine1: $line")
                if(line != null){
                    startActivity(line)
                    Log.w("zio","intentLine2: $line")
                } else {
                    Toast.makeText(this,"라인을 설치하세요",Toast.LENGTH_SHORT).show()
                }
            }catch (e : Exception){
                Log.w("zio","error : $e")
            }
        }
    }
    override fun onBackPressed() {
        if(System.currentTimeMillis() - backPressTime < 2_000){
            finishAffinity()
        }else{
            Toast.makeText(this,"한버 더 누르면 앱이 종료됩니다.",Toast.LENGTH_SHORT).show()
            backPressTime = System.currentTimeMillis()
        }
    }
}