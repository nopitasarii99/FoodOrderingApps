package com.gilang.loginregister

import android.content.Intent
import android.widget.Button
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView

class GagalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gagal)
        supportActionBar?.hide()

        // Membuat Animasi Gif Aktif
        val imageView = findViewById<ImageView>(R.id.ivSuccess1)
        Glide.with(this)
            .asGif()
            .load(R.drawable.gagal)
            .into(imageView)

        // Tombol Home untuk kembali ke Activity_Home
        val btnHome: Button = findViewById(R.id.btnHome1)
        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish() // Tutup Activity_Berhasil agar tidak kembali ke sini
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}