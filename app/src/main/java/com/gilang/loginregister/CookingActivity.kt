package com.gilang.loginregister

import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.Gravity
import android.content.SharedPreferences
import android.content.Intent
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView


class CookingActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_cooking)

        val iv_Back = findViewById<ImageView>(R.id.iv_back)
        // Menangani klik pada tombol kembali (backButton)
        iv_Back.setOnClickListener {
            // Pindahkan ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Hapus ShoppingActivity dari stack
        }


        // Temukan TextView dengan ID tv_teks_info
        val tvTeksInfo = findViewById<TextView>(R.id.tv_teks_info)

        // Buat animasi berkedip (fade in dan fade out)
        val blinkAnimation = AlphaAnimation(1.0f, 0.0f) // Dari 1 (terlihat) ke 0 (transparan)
        blinkAnimation.duration = 1000 // Durasi dalam milidetik (1000 ms = 1 detik)
        blinkAnimation.repeatMode = AlphaAnimation.REVERSE // Efek balik (dari transparan ke terlihat)
        blinkAnimation.repeatCount = AlphaAnimation.INFINITE // Ulang terus menerus

        // Terapkan animasi pada TextView
        tvTeksInfo.startAnimation(blinkAnimation)

        // Set text pada TextView
        tvTeksInfo.text = "Sedang memasak...."

        val tvKeterangan = findViewById<TextView>(R.id.tv_keterangan)

        // Memusatkan teks secara horizontal dan vertikal
        tvKeterangan.gravity = Gravity.CENTER

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)

        // Temukan tombol dengan ID btn_terima_kasih
        val btnTerimaKasih = findViewById<Button>(R.id.btn_terima_kasih)

        // Set OnClickListener untuk btn_terima_kasih
        btnTerimaKasih.setOnClickListener {
            // Simpan status akses Chat dan Cooking menjadi false
            val editor = sharedPreferences.edit()
            editor.putBoolean("canAccessChat", false) // Nonaktifkan Chat
            editor.putBoolean("canAccessCooking", false) // Nonaktifkan Cooking
            editor.apply()


            // Kembali ke halaman Home
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Tutup Activity Cooking
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}