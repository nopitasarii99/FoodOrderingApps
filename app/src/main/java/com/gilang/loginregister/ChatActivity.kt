package com.gilang.loginregister

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        supportActionBar?.hide()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageView>(R.id.backButton)

        // Sembunyikan semua pesan yang belum muncul
        val messageLayout1 = findViewById<View>(R.id.messageLayout1)
        messageLayout1.visibility = View.GONE

        val buyerMessageLayout = findViewById<View>(R.id.buyerMessageLayout)
        buyerMessageLayout.visibility = View.GONE

        val buyerMessageLayout1 = findViewById<View>(R.id.buyerMessageLayout1)
        buyerMessageLayout1.visibility = View.GONE

        // Tampilkan chat admin setelah 2 detik
        Handler(Looper.getMainLooper()).postDelayed({
            showAdminMessage()
        }, 2000)

        // Atur tombol kirim untuk menampilkan chat pembeli
        val sendButton = findViewById<View>(R.id.sendButton)
        val messageInput = findViewById<EditText>(R.id.messageInput)

        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                // Jika admin telah membalas, tampilkan pesan pembeli
                if (messageLayout1.visibility == View.VISIBLE) {
                    displayBuyerMessage1(messageText)
                    messageInput.text.clear()

                    // Setelah pesan pembeli terkirim, tampilkan balasan admin dalam 2 detik
                    Handler(Looper.getMainLooper()).postDelayed({
                        showAdminReplyMessage()
                    }, 2000)
                } else {
                    // Jika belum ada balasan dari admin, tampilkan chat pembeli pertama
                    displayBuyerMessage(messageText)
                    messageInput.text.clear()

                    // Setelah pesan pembeli terkirim, tampilkan balasan admin dalam 2 detik
                    Handler(Looper.getMainLooper()).postDelayed({
                        showAdminReplyMessage()
                    }, 2000)
                }
            }
        }

        // Menangani klik pada tombol kembali (backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Hapus ChatActivity dari stack
        }
    }

    private fun showAdminMessage() {
        val adminMessageLayout = findViewById<View>(R.id.messageLayout)
        adminMessageLayout.visibility = View.VISIBLE
    }

    private fun displayBuyerMessage(message: String) {
        val buyerMessageLayout = findViewById<View>(R.id.buyerMessageLayout)
        val buyerMessageText = buyerMessageLayout.findViewById<TextView>(R.id.buyerMessageText)

        buyerMessageLayout.visibility = View.VISIBLE
        buyerMessageText.text = message
    }

    private fun displayBuyerMessage1(message: String) {
        val buyerMessageLayout1 = findViewById<View>(R.id.buyerMessageLayout1)
        val buyerMessageText1 = buyerMessageLayout1.findViewById<TextView>(R.id.buyerMessageText1)

        buyerMessageLayout1.visibility = View.VISIBLE
        buyerMessageText1.text = message
    }

    private fun showAdminReplyMessage() {
        val messageLayout1 = findViewById<View>(R.id.messageLayout1)
        messageLayout1.visibility = View.VISIBLE

        val messageText1 = findViewById<TextView>(R.id.messageText1)
        messageText1.text = "Baik ka kami akan mengirimkan pesanan ke nomor meja anda."
    }
}

