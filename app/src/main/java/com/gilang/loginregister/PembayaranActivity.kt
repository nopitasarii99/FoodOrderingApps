package com.gilang.loginregister

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast



class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_pembayaran)


        // Data untuk menghubungkan harga total Activity_Shopping & Activity_Pembayaran
        val totalCardTextView: TextView = findViewById(R.id.TotalCard)
        val totalValue = intent.getStringExtra("TOTAL_VALUE")
        totalCardTextView.text = "Total Pembelian\n$totalValue" // Tampilkan data



        // Tombol "Sudah Bayar"
        val btnSudahBayar = findViewById<Button>(R.id.btnSudahBayar)
        btnSudahBayar.setOnClickListener {
        // Membuka Activity_Berhasil saat tombol "Sudah Bayar" diklik
        val intent = Intent(this, BerhasilActivity::class.java)
        // Mengambil nilai dari TotalCard
        val totalValue = totalCardTextView.text.toString().replace("Total Pembelian\n", "")
        // Mengirimkan nilai total ke BerhasilActivity
        intent.putExtra("TOTAL_VALUE", totalValue)
        startActivity(intent)
        }



        // Tombol "Batalkan Pesanan"
        val btnBatalkanPesanan = findViewById<Button>(R.id.btnBatalkanPesanan)
        btnBatalkanPesanan.setOnClickListener {
            val intent = Intent(this, GagalActivity::class.java)
            startActivity(intent)
            finish() // Tutup PembayaranActivity
        }



        // Kembali ke activity_Shopping
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Kembali ke activity sebelumnya
        }

        // Di dalam Activity_Pembayaran.kt
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        btnSudahBayar.setOnClickListener {
            // Set status bahwa tombol chat bisa diakses
            editor.putBoolean("canAccessChat", true)
            editor.apply()

            // Lanjutkan dengan logika pembayaran lainnya
            // Misalnya berpindah ke halaman berikutnya
            val intent = Intent(this, BerhasilActivity::class.java)
            startActivity(intent)
        }

        btnSudahBayar.setOnClickListener {
            // Simpan status bahwa pembayaran telah dilakukan
            val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("canAccessCooking", true) // Set akses menjadi true
            editor.putBoolean("canAccessChat", true) // Set akses menjadi true

            editor.apply()

            // Berikan pesan konfirmasi
            Toast.makeText(this, "Pembayaran berhasil! Cooking dapat diakses.", Toast.LENGTH_SHORT).show()

            // Pindah ke halaman utama (opsional)
            val intent = Intent(this, BerhasilActivity::class.java)
            startActivity(intent)
            finish()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}