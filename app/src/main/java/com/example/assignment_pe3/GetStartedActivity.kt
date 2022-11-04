package com.example.assignment_pe3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class GetStartedActivity : AppCompatActivity() {
    lateinit var tvLogin: TextView
    lateinit var btnGetStarted: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started)

        tvLogin = findViewById<TextView>(R.id.tv_login)
        btnGetStarted = findViewById<Button>(R.id.btn_getStarted)

        tvLogin.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnGetStarted.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}