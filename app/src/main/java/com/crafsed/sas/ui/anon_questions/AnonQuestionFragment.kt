package com.crafsed.sas.ui.anon_questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crafsed.sas.databinding.FragmentAnonQuestion2Binding
import com.crafsed.sas.ui.MainActivity
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AnonQuestionFragment : Fragment() {
    private var _binding: FragmentAnonQuestion2Binding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnonQuestion2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.questionClose.setOnClickListener {
            (requireActivity() as MainActivity).toListExpandBottomSheet()
        }
        binding.questionSend.setOnClickListener {
            send(binding.questionHeaderText.text.toString(),binding.enterQuestionET.text.toString() )
            binding.enterQuestionET.text?.clear()
            binding.questionHeaderText.text?.clear()
        }
    }

    private fun send(header: String, text: String ) {
        if (text.isNotBlank() && header.isNotBlank())
            viewModel.sendAnonQuestion(header, text)
    }
}