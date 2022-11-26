package com.crafsed.sas.ui

import android.content.res.Resources.getSystem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crafsed.sas.R
import com.crafsed.sas.databinding.ActivityMainListBinding
import com.crafsed.sas.ui.anon_questions.AnonQuestionFragment
import com.crafsed.sas.ui.lectures.FragmentLecture
import com.crafsed.sas.ui.lectures.ListFragment
import com.crafsed.sas.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainListBinding
    private val loginViewModel: LoginViewModel by viewModel()

    private var listFragment = ListFragment()
        get(){
            if (field.isDetached) {
                field = ListFragment()
            }
            return field
        }
    private var lectureFragment = FragmentLecture()
        get(){
            if (field.isDetached) {
                field = FragmentLecture()
            }
            return field
        }
    private var questionsFragment = AnonQuestionFragment()
        get(){
            if (field.isDetached) {
                field = AnonQuestionFragment()
            }
            return field
        }
    private var quizFragment = QuizFragment()
        get(){
            if (field.isDetached) {
                field = QuizFragment()
            }
            return field
        }

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

    fun toQuiz(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, quizFragment).commit()
    }

    fun toQuestions(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, questionsFragment).commit()
    }

}

val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

