package com.crafsed.sas.ui.quizes.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crafsed.sas.databinding.FragmentLectureBinding
import com.crafsed.sas.databinding.FragmentTestSeveralAnsversBinding
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ManyQuiz : Fragment() {
    private var _binding: FragmentTestSeveralAnsversBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    lateinit var answData: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestSeveralAnsversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.check1.text = answData[0]
        binding.check2.text = answData[1]
        binding.check3.text = answData[2]
        binding.check4.text = answData[3]
    }

    fun getAnswers(): List<String> {
        val one = binding.check1.isChecked
        val two = binding.check2.isChecked
        val three = binding.check3.isChecked
        val four = binding.check4.isChecked

        return ArrayList<String>().apply {
            if (one) add("1")
            if (two) add("2")
            if (three) add("3")
            if (four) add("4")
        }
    }

    fun setAnswers(answers: List<String>) {
        answData = answers
        if (isResumed) {
            binding.check1.text = answData[0]
            binding.check2.text = answData[1]
            binding.check3.text = answData[2]
            binding.check4.text = answData[3]
        }
    }
}