package com.gilang.loginregister

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun    onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Inisialisasi tombol login
        val loginButton = findViewById<Button>(R.id.btn_1)
        loginButton.setOnClickListener {
            // Berpindah ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Inisialisasi tombol Register
        val registerButton = findViewById<Button>(R.id.btn_2)
        registerButton.setOnClickListener {
            // Pindah ke RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            }

        }
    }