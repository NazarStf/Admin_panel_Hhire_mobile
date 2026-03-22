package com.example.admin_panel_hhire_mobile.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.admin_panel_hhire_mobile.R
import com.example.admin_panel_hhire_mobile.databinding.ActivityLoginBinding
import android.widget.Toast
import com.example.admin_panel_hhire_mobile.MainActivity
import android.content.Intent

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.etLogin
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnLogin.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (login == "admin" && password == "admin") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}