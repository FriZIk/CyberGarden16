package com.crafsed.sas.ui.quizes.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.crafsed.sas.R
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SingleQuiz : Fragment(R.layout.fragmen_single_test) {
    private lateinit var answersRadioGroup: RadioGroup

    private lateinit var one: RadioButton
    private lateinit var two: RadioButton
    private lateinit var three: RadioButton
    private lateinit var four: RadioButton

    lateinit var answData: List<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        answersRadioGroup = view.findViewById(R.id.answers)

        one = view.findViewById(R.id.answ1)
        two = view.findViewById(R.id.answ2)
        three = view.findViewById(R.id.answ3)
        four = view.findViewById(R.id.answ4)
    }

    override fun onResume() {
        super.onResume()
        one.text = answData[0]
        two.text = answData[1]
        three.text = answData[2]
        four.text = answData[3]
    }

    fun getAnswers(): List<String> {
        val answ = answersRadioGroup.checkedRadioButtonId

        val answer = when (answ) {
            R.id.answ1 -> 1
            R.id.answ2 -> 2
            R.id.answ3 -> 3
            R.id.answ4 -> 4
            else -> 0
        }

        return listOf(answer.toString())
    }

    fun setAnswers(answers: List<String>) {
        answData = answers
        if (isResumed) {
            one.text = answData[0]
            two.text = answData[1]
            three.text = answData[2]
            four.text = answData[3]
        }
    }
}