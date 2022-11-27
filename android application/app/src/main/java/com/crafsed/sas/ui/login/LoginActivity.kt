package com.crafsed.sas.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.crafsed.sas.R
import com.crafsed.sas.databinding.ActivityLoginBinding
import com.crafsed.sas.ui.MainActivity
import com.crafsed.sas.vm.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val loginFragment = LoginFragment()

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
        intent.putExtra("lector", viewModel.isLector.value == true)
        intent.putExtra("token", viewModel.token)
        startActivity(intent)
        println(viewModel.isLector.value)
        finish()
    }
}