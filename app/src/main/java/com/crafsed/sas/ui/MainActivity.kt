package com.crafsed.sas.ui

import android.content.res.Resources.getSystem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crafsed.sas.R
import com.crafsed.sas.databinding.ActivityLoginBinding
import com.crafsed.sas.databinding.ActivityMainListBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainListBinding

    private val listFragment = ListFragment()
    private val lectureFragment = FragmentLecture()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, listFragment).commit()
    }

    fun toLecture() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, lectureFragment).commit()
    }

    fun toList() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, listFragment).commit()
    }
}

val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

