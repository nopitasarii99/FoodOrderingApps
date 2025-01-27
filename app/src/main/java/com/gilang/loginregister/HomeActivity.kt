package com.gilang.loginregister

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.ViewFlipper
import android.widget.Toast


class HomeActivity : AppCompatActivity() {

    private lateinit var promoBanner: ViewFlipper

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        promoBanner = findViewById(R.id.promoBanner)

        // Mulai ViewFlipper untuk menampilkan iklan secara otomatis
        promoBanner.startFlipping()

        // Ambil referensi ke LinearLayout dengan ID layoutShopping
        val layoutShopping = findViewById<LinearLayout>(R.id.layoutShopping)

        sharedPreferences = getSharedPreferences("ShoppingPrefs", MODE_PRIVATE)

        // Mengakses elemen-elemen menggunakan findViewById
        val cartButton1 = findViewById<ImageButton>(R.id.cartButton1)
        val menuName1 = findViewById<TextView>(R.id.menuName1)
        val menuPrice1 = findViewById<TextView>(R.id.menuPrice1)


        // Menambahkan item ke keranjang belanja saat tombol cartButton1 diklik
        cartButton1.setOnClickListener {
            val itemName = menuName1.text.toString()
            val itemPrice = menuPrice1.text.toString()
            val itemImageResId = R.drawable.nasi3 // Gambar yang sesuai dengan menu
            addToCart(itemName, itemPrice,  itemImageResId)
        }

        // Set klik listener untuk berpindah ke ShoppingActivity
        layoutShopping.setOnClickListener {
            val intent = Intent(this, ShoppingActivity::class.java)
            startActivity(intent)
        }

        val layoutChat = findViewById<LinearLayout>(R.id.layoutChat)
        // Set OnClickListener untuk beralih ke Activity_Chat
        layoutChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        // Temukan elemen layoutCooking
        val layoutCooking = findViewById<LinearLayout>(R.id.layoutCooking)

        // Set click listener untuk layoutCooking
        layoutCooking.setOnClickListener {
            // Ketika layoutCooking diklik, buka Activity_Cooking
            val intent = Intent(this, CookingActivity::class.java)
            startActivity(intent)
        }


        // Di dalam HomeActivity.kt
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val canAccessChat = sharedPreferences.getBoolean("canAccessChat", false)

        if (canAccessChat) {
            // Set OnClickListener untuk layoutChat jika bisa diakses
            layoutChat.setOnClickListener {
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }
        } else {
            // Layout Chat tidak bisa diakses, mungkin sembunyikan atau beri pesan
            layoutChat.setOnClickListener {
                Toast.makeText(this, "Chat dapet dilakukan setelah melakukan pembayaran", Toast.LENGTH_SHORT).show()
            }
        }


        // Ambil status akses dari SharedPreferences
        val canAccessCooking = sharedPreferences.getBoolean("canAccessCooking", false)

        if (canAccessCooking) {
            // Set OnClickListener untuk layoutCooking jika akses diperbolehkan
            layoutCooking.setOnClickListener {
                val intent = Intent(this, CookingActivity::class.java)
                startActivity(intent)
            }
        } else {
            // Jika akses tidak diperbolehkan, tampilkan pesan
            layoutCooking.setOnClickListener {
                Toast.makeText(this, "Akses ke Cooking hanya tersedia setelah pembayaran.", Toast.LENGTH_SHORT).show()
            }
        }



        // Set logika akses Chat
        if (canAccessChat) {
            layoutChat.setOnClickListener {
                val intent = Intent(this, ChatActivity::class.java)
                startActivity(intent)
            }
        } else {
            layoutChat.setOnClickListener {
                Toast.makeText(this, "Chat tidak dapat diakses.", Toast.LENGTH_SHORT).show()
            }
        }

        // Set logika akses Cooking
        if (canAccessCooking) {
            layoutCooking.setOnClickListener {
                val intent = Intent(this, CookingActivity::class.java)
                startActivity(intent)
            }
        } else {
            layoutCooking.setOnClickListener {
                Toast.makeText(this, "Cooking tidak dapat diakses.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun addToCart(itemName: String, itemPrice: String, itemImageResId: Int) {
        // Simpan item ke SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("itemName", itemName)
        editor.putString("itemPrice", itemPrice)
        editor.putInt("itemQuantity", 1) // Memulai dengan 1
        editor.putInt("itemImageResId", itemImageResId) // Simpan ID resource gambar
        editor.apply()

        // Pindah ke ShoppingActivity
        val intent = Intent(this, ShoppingActivity::class.java)
        startActivity(intent)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}