package com.example.assignment_pe3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    lateinit var btnRegister:Button
    lateinit var tvLogin:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister = findViewById<Button>(R.id.btn_register)
        tvLogin = findViewById<TextView>(R.id.tv_login)

        tvLogin.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}