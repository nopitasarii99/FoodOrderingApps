package com.gilang.loginregister

import android.content.Intent
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.Bundle
import android.text.InputType
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val editEmail: EditText = findViewById(R.id.edit_email)
        val editKataSandi: EditText = findViewById(R.id.edit_kata_sandi)
        val btnLogin: Button = findViewById(R.id.L_btn_1)

        btnLogin.setOnClickListener {
            val email = editEmail.text.toString()
            val kataSandi = editKataSandi.text.toString()

            // Mendapatkan SharedPreferences untuk mengecek kredensial
            val sharedPreferences: SharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
            val storedEmail = sharedPreferences.getString("email", null)
            val storedPassword = sharedPreferences.getString("password", null)

            // Validasi login
            if (email == storedEmail && kataSandi == storedPassword) {
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()

                // Arahkan ke halaman utama
                startActivity(Intent(this, HomeActivity::class.java))
                finish() // Menutup LoginActivity
            }
            else {
                Toast.makeText(this, "Email atau Kata Sandi salah", Toast.LENGTH_SHORT).show()
            }
        }

        val L_img_12: ImageView = findViewById(R.id.L_img_12)
        btnBackloginListener(L_img_12)

        val txtRegister: TextView = findViewById(R.id.txt_register)
        txtRegisterListener(txtRegister)

        // Tambahkan referensi untuk EditText dan ImageView
        val passwordEditText: EditText = findViewById(R.id.edit_kata_sandi)
        val showPasswordIcon: ImageView = findViewById(R.id.showPasswordIcon)

        // Handle klik ikon mata untuk toggle password visibility
        showPasswordIcon.setOnClickListener {
            if (passwordEditText.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                showPasswordIcon.setImageResource(R.drawable.ic_visibility_off)
            }
            else {
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordIcon.setImageResource(R.drawable.ic_visibility_off)
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun btnBackloginListener(L_img_12: ImageView) {
        L_img_12.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun txtRegisterListener(txtRegister: TextView) {
        txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
