package com.crafsed.sas.ui.quizes

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.crafsed.sas.R
import com.crafsed.sas.data.QuizQuestionData
import com.crafsed.sas.data.QuizesData
import com.crafsed.sas.databinding.FragmentTestBinding
import com.crafsed.sas.ui.MainActivity
import com.crafsed.sas.ui.quizes.common.ManyQuiz
import com.crafsed.sas.ui.quizes.common.SingleQuiz
import com.crafsed.sas.ui.quizes.common.TextQuiz
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class QuizFragment : Fragment(R.layout.fragment_test) {
    private var singleFragment = SingleQuiz()
        get() {
            if (field.isDetached) {
                field = SingleQuiz()
            }
            return field
        }
    private var manyFragment = ManyQuiz()
        get() {
            if (field.isDetached) {
                field = ManyQuiz()
            }
            return field
        }
    private var textFragment = TextQuiz()
        get() {
            if (field.isDetached) {
                field = TextQuiz()
            }
            return field
        }


    private var _binding: FragmentTestBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    lateinit var questions: QuizesData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    var currentCuestion = 0

    private fun initiateTimer() {
        val time =
            (questions!!.openingTime / 1000) - (System.currentTimeMillis() / 1000) + questions.seconds

        object : CountDownTimer(time * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.timer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                viewModel.postQuizResult(viewModel.currentQuizResult, questions.id)
                (requireActivity() as MainActivity).endQuizTime()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questions = viewModel.currentQuiz.value!!

        binding.backBar.setOnClickListener {
            val alertDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
            alertDialog.setTitle("Конец теста")
            alertDialog.setMessage("Вы хотите закончить попытку?")
            alertDialog.setButton(
                AlertDialog.BUTTON_NEGATIVE, "Отмена"
            ) { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "ДА"
            ) { dialog, which ->
                dialog.dismiss()
                (requireActivity() as MainActivity).endQuiz()
            }
            currentCuestion--
            alertDialog.show()
        }

        binding.barQuestionNum.text = "Вопрос ${(currentCuestion + 1)}"
        binding.barTestName.text = questions?.quizName
        binding.currentQuestion.text = "${(currentCuestion + 1)} / ${questions?.questions?.size}"
        binding.question.text = questions.questions[currentCuestion].question

        initiateTimer()

        //first question
        setMicrofragmentAndAnswers()

        if (questions.questions.size == 1) {
            binding.nextBtn.text = "Закончить"
        }

        questions.questions.forEach {
            viewModel.currentQuizResult.add(emptyList())
        }

        binding.nextBtn.setOnClickListener {
            viewModel.currentQuizResult[currentCuestion] =
                when (questions.questions[currentCuestion].type) {
                    QuizQuestionData.QType.MANY -> {
                        manyFragment.getAnswers()
                    }
                    QuizQuestionData.QType.ONE -> {
                        singleFragment.getAnswers()
                    }
                    QuizQuestionData.QType.TEXT -> {
                        textFragment.getAnswers()
                    }
                }

            if (questions.questions.size == currentCuestion + 2) {
                binding.nextBtn.text = "Закончить"
            }

            if (questions.questions.size == currentCuestion + 1) {
                val alertDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
                alertDialog.setTitle("Конец теста")
                alertDialog.setMessage("Вы хотите закончить попытку?")
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE, "Отменв"
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "ДА"
                ) { dialog, which ->
                    dialog.dismiss()
                    (requireActivity() as MainActivity).endQuiz()
                }
                currentCuestion--
                alertDialog.show()
            }
            currentCuestion++
            binding.barQuestionNum.text = "Вопрос ${(currentCuestion + 1)}"
            binding.currentQuestion.text =
                "${(currentCuestion + 1)} / ${questions?.questions?.size}"
            binding.question.text = questions.questions[currentCuestion].question
            setMicrofragmentAndAnswers()
        }
        binding.prevBtn.setOnClickListener {
            viewModel.currentQuizResult[currentCuestion] =
                when (questions.questions[currentCuestion].type) {
                    QuizQuestionData.QType.MANY -> {
                        manyFragment.getAnswers()
                    }
                    QuizQuestionData.QType.ONE -> {
                        singleFragment.getAnswers()
                    }
                    QuizQuestionData.QType.TEXT -> {
                        textFragment.getAnswers()
                    }
                }
            binding.nextBtn.text = "Следующий"

            currentCuestion--
            if (currentCuestion == -1) currentCuestion = 0

            binding.barQuestionNum.text = "Вопрос ${(currentCuestion + 1)}"
            binding.currentQuestion.text =
                "${(currentCuestion + 1)} / ${questions?.questions?.size}"
            binding.question.text = questions.questions[currentCuestion].question
            setMicrofragmentAndAnswers()
        }
    }

    fun setMicrofragmentAndAnswers() {
        when (questions!!.questions[currentCuestion].type) {
            QuizQuestionData.QType.MANY -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.testAnswersFrame, manyFragment).commit()
                manyFragment.setAnswers(questions.questions[currentCuestion].answers)
            }
            QuizQuestionData.QType.ONE -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.testAnswersFrame, singleFragment).commit()

                singleFragment.setAnswers(questions.questions[currentCuestion].answers)
            }
            QuizQuestionData.QType.TEXT -> {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.testAnswersFrame, textFragment).commit()
                textFragment.setAnswers(questions.questions[currentCuestion].answers)
            }
        }
    }
}