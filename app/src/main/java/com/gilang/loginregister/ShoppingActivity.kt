package com.gilang.loginregister

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.SharedPreferences
import android.widget.TextView
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

class ShoppingActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("ShoppingPrefs", MODE_PRIVATE)

        // Mengakses elemen-elemen menggunakan findViewById
        val itemContainer = findViewById<LinearLayout>(R.id.itemContainer)
        val itemName = findViewById<TextView>(R.id.itemName)
        val itemPrice = findViewById<TextView>(R.id.itemPrice)
        val checkoutButton = findViewById<Button>(R.id.checkoutButton)
        val itemCountTextView = findViewById<TextView>(R.id.itemCount)
        val addButton = findViewById<Button>(R.id.addButton)
        val removeButton = findViewById<Button>(R.id.removeButton)
        val itemDelete = findViewById<TextView>(R.id.itemDelete)
        val subTotalTextView = findViewById<TextView>(R.id.subTotal)
        val deliveryFeeTextView = findViewById<TextView>(R.id.deliveryFee)
        val totalTextView = findViewById<TextView>(R.id.total)
        val backButton = findViewById<ImageView>(R.id.backButton)

        val itemImage = findViewById<ImageView>(R.id.itemImage)
        // Ambil ID resource gambar dari SharedPreferences
        val itemImageResId = sharedPreferences.getInt("itemImageResId", R.drawable.nasi3)
        itemImage.setImageResource(itemImageResId)

        // Ambil data item dari SharedPreferences
        val itemNameText = sharedPreferences.getString("itemName", "")
        val itemPriceText = sharedPreferences.getString("itemPrice", "Rp.0.00")
        var itemQuantity = sharedPreferences.getInt("itemQuantity", 0)

        // Menyembunyikan itemContainer jika tidak ada pesanan
        if (itemNameText.isNullOrEmpty() || itemPriceText == "Rp.0.00" || itemQuantity == 0) {
            itemContainer.visibility = LinearLayout.GONE
            findViewById<LinearLayout>(R.id.paymentDetails).visibility = LinearLayout.GONE
            checkoutButton.isEnabled = false
            checkoutButton.setBackgroundColor(resources.getColor(R.color.gray)) // Ubah warna tombol untuk menandakan tidak aktif
        } else {
            itemContainer.visibility = LinearLayout.VISIBLE
            findViewById<LinearLayout>(R.id.paymentDetails).visibility = LinearLayout.VISIBLE
            checkoutButton.isEnabled = true
            checkoutButton.setBackgroundColor(resources.getColor(R.color.orange)) // Kembalikan warna tombol yang aktif
        }

        // Konversi harga satuan ke Double
        val unitPrice = convertPriceToDouble(itemPriceText ?: "Rp.0.00")

        // Menangani tombol Checkout
        checkoutButton.setOnClickListener {
            if (itemNameText.isNullOrEmpty() || itemPriceText == "Rp.0.00" || itemQuantity == 0) {
                // Jika tidak ada item, tampilkan pesan peringatan
                Toast.makeText(this, "Silakan pilih pesanan terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
                // Jika ada item, lanjutkan ke Activity_Pembayaran
                val totalValue = totalTextView.text.toString() // Ambil nilai total
                val intent = Intent(this, PembayaranActivity::class.java)
                intent.putExtra("TOTAL_VALUE", totalValue) // Kirim data total
                startActivity(intent)
            }
        }

        // Menampilkan informasi item yang dipilih
        itemName.text = itemNameText
        itemPrice.text = formatPrice(unitPrice * itemQuantity)
        itemCountTextView.text = itemQuantity.toString()

        // Hitung subtotal, biaya pengiriman, dan total
        updatePriceCalculations(unitPrice, itemQuantity, subTotalTextView, deliveryFeeTextView, totalTextView)

        // Menangani tombol add (+)
        addButton.setOnClickListener {
            itemQuantity++
            itemCountTextView.text = itemQuantity.toString()
            itemPrice.text = formatPrice(unitPrice * itemQuantity) // Update harga total

            // Simpan ke SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putInt("itemQuantity", itemQuantity)
            editor.apply()

            // Update kalkulasi harga
            updatePriceCalculations(unitPrice, itemQuantity, subTotalTextView, deliveryFeeTextView, totalTextView)
        }

        // Menangani tombol remove (-)
        removeButton.setOnClickListener {
            if (itemQuantity > 1) {
                itemQuantity--
                itemCountTextView.text = itemQuantity.toString()
                itemPrice.text = formatPrice(unitPrice * itemQuantity) // Update harga total

                // Simpan ke SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putInt("itemQuantity", itemQuantity)
                editor.apply()

                // Update kalkulasi harga
                updatePriceCalculations(unitPrice, itemQuantity, subTotalTextView, deliveryFeeTextView, totalTextView)
            }
        }

        // Menangani klik pada tombol "Delete"
        itemDelete.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.remove("itemName")
            editor.remove("itemPrice")
            editor.remove("itemQuantity")
            editor.apply()

            // Reset tampilan
            itemName.text = ""
            itemPrice.text = "Rp.0.00"
            itemCountTextView.text = "0"
            subTotalTextView.text = "Subtotal: Rp.0.00"
            deliveryFeeTextView.text = "Pajak PPN: Rp.0.00"
            totalTextView.text = "Total: Rp.0.00"

            // Sembunyikan itemContainer dan paymentDetails jika data dihapus
            itemContainer.visibility = LinearLayout.GONE
            findViewById<LinearLayout>(R.id.paymentDetails).visibility = LinearLayout.GONE

            // Menonaktifkan tombol checkout jika tidak ada pesanan
            checkoutButton.isEnabled = false
            checkoutButton.setBackgroundColor(resources.getColor(R.color.gray)) // Ubah warna tombol untuk menandakan tidak aktif

            Log.d("ShoppingActivity", "Item data deleted")
        }

        // Menangani klik pada tombol kembali (backButton)
        backButton.setOnClickListener {
            // Pindahkan ke HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Hapus ShoppingActivity dari stack
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Fungsi untuk mengonversi harga dari format string ke angka (Double)
    private fun convertPriceToDouble(price: String): Double {
        // Hapus simbol "Rp." dan titik dari harga
        val cleanedPrice = price.replace("Rp.", "").replace(".", "").trim()
        return cleanedPrice.toDoubleOrNull() ?: 0.0 // Mengonversi ke Double atau 0.0 jika gagal
    }

    // Fungsi untuk memformat harga ke format yang sesuai
    private fun formatPrice(price: Double): String {
        return "Rp.%,.0f".format(price) // Menggunakan format untuk menambahkan titik sebagai pemisah ribuan
    }

    // Fungsi untuk menghitung dan memperbarui Subtotal, Pajak PPN, dan Total
    private fun updatePriceCalculations(unitPrice: Double, itemQuantity: Int, subTotalTextView: TextView, deliveryFeeTextView: TextView, totalTextView: TextView) {
        val subTotal = unitPrice * itemQuantity
        val taxPPN = subTotal * 0.02 // Pajak PPN 2% dari subtotal
        val total = subTotal + taxPPN

        // Update UI
        subTotalTextView.text = "Subtotal: ${formatPrice(subTotal)}"
        deliveryFeeTextView.text = "Pajak PPN: ${formatPrice(taxPPN)}"
        totalTextView.text = "Total: ${formatPrice(total)}"
    }
}
