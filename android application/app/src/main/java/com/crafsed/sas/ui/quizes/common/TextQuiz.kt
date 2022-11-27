package com.crafsed.sas.ui.quizes.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crafsed.sas.databinding.FragmentLectureBinding
import com.crafsed.sas.databinding.FragmentTestTextBinding
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TextQuiz : Fragment() {
    private var _binding: FragmentTestTextBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun getAnswers() : List<String> {
        return listOf(binding.testTextInput.text.toString())
    }

    fun setAnswers(answers: List<String>) {
        if (isResumed) {
            binding.testTextInput.text.clear()
        }
    }
}