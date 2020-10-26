package com.vickyfebiola_18104022.lifecycleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    fun printState(msg: String) {
        Log.d("ActivityState",msg)
        Toast.makeText(applicationContext,msg, Toast.LENGTH_SHORT).show()}

    override fun onStart(){
        super.onStart()
        printState("Halaman satu On Start")
    }

    override fun onResume(){
        super.onResume()
        printState("Halaman satu On Resume")
    }

    override fun onPause(){
        super.onPause()
        printState("Halaman satu On Pause")
    }

    override fun onStop(){
        super.onStop()
        printState("Halaman satu On Stop")
    }

    override fun onRestart(){
        super.onRestart()
        printState("Halaman satu On Restart")
    }

    override fun onDestroy() {
        super.onDestroy()
        printState("Halaman satu On Destroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnPage = findViewById<Button>(R.id.btnPage)
        btnPage.setOnClickListener{
            val intent = Intent(this, HalamanDuaActivity::class.java)
            startActivity(intent)
        }
    }
}