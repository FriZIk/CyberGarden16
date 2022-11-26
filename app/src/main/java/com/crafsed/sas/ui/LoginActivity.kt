package com.crafsed.sas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.crafsed.sas.R
import com.crafsed.sas.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, loginFragment)
            .commit()
    }
    override fun onStart() {
        super.onStart()
    }

    fun toMainFragment() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}