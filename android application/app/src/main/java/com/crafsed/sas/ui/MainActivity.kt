package com.crafsed.sas.ui

import android.content.res.Resources.getSystem
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.crafsed.sas.R
import com.crafsed.sas.databinding.ActivityMainListBinding
import com.crafsed.sas.ui.anon_questions.AnonQuestionFragment
import com.crafsed.sas.ui.lectures.FragmentLecture
import com.crafsed.sas.ui.lectures.FragmentLecturePrepod
import com.crafsed.sas.ui.lectures.ListFragment
import com.crafsed.sas.ui.quizes.QuizFragment
import com.crafsed.sas.vm.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainListBinding

    private val mainViewModel: MainViewModel by viewModel()

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
    private var lectureFragmentPrepod = FragmentLecturePrepod()
        get(){
            if (field.isDetached) {
                field = FragmentLecturePrepod()
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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        mainViewModel.isLector = intent.getBooleanExtra("lector", false)
        mainViewModel.token = intent.getStringExtra("token").orEmpty()

        binding = ActivityMainListBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, listFragment).commit()
    }

    fun toLecture() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, lectureFragment).commit()
    }

    fun toLecturePrep() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, lectureFragment).commit()
    }

    fun toList() {
        mainViewModel.bsExpand = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, listFragment).commit()
    }

    fun toListExpandBottomSheet() {
        mainViewModel.bsExpand = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameMain, listFragment).commit()
    }

    fun toQuiz(){
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFrameMain, quizFragment).commit()
    }

    fun toQuestions(){
        supportFragmentManager.beginTransaction()
            .add(R.id.mainFrameMain, questionsFragment).commit()
    }

    override fun onBackPressed() {
        if (listFragment.bs.state == STATE_EXPANDED) {
            listFragment.bs.state = STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }

    fun endQuizTime() {
        toList()
        Toast.makeText(this, "Время вышло. Тест сохранён и отправлен", Toast.LENGTH_SHORT).show()
    }

    fun endQuiz() {
        toList()
        Toast.makeText(this, "Тест сохранён и отправлен", Toast.LENGTH_SHORT).show()
    }
}

val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()

