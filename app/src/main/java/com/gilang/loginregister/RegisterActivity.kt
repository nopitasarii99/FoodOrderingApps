package com.gilang.loginregister

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import android.widget.Button
import android.widget.EditText

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        // Mendapatkan referensi EditText dan Button
        val editNamaLengkap: EditText = findViewById(R.id.edit_nama_lengkap)
        val editEmail: EditText = findViewById(R.id.edit_email)
        val editKataSandi: EditText = findViewById(R.id.edit_kata_sandi)
        val editUlangKataSandi: EditText = findViewById(R.id.edit_Ulang_kata_sandi)
        val btnRegister: Button = findViewById(R.id.R_btn_1)

        // Menambahkan listener pada tombol back
        val backButton = findViewById<ImageView>(R.id.R_img_5)
        backButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Menutup RegisterActivity
        }

        // Listener untuk tombol Register
        btnRegister.setOnClickListener {
            val namaLengkap = editNamaLengkap.text.toString()
            val email = editEmail.text.toString()
            val kataSandi = editKataSandi.text.toString()
            val ulangiKataSandi = editUlangKataSandi.text.toString()

            // Validasi input
            if (namaLengkap.isEmpty() || email.isEmpty() || kataSandi.isEmpty() || ulangiKataSandi.isEmpty()) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (kataSandi != ulangiKataSandi) {
                Toast.makeText(this, "Kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Simpan data ke SharedPreferences atau Database untuk login nanti
            val sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("email", email)
            editor.putString("password", kataSandi)
            editor.apply()

            // Setelah berhasil registrasi, arahkan ke halaman login
            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Menutup RegisterActivity
        }

        // Menambahkan listener pada textLogin untuk membuka halaman LoginActivity
        val txtLogin: TextView = findViewById(R.id.txt_login)
        txtLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Menutup RegisterActivity
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            }
        }
    }
