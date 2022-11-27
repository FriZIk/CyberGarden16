package com.crafsed.sas.ui.lectures

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.crafsed.sas.R
import com.crafsed.sas.data.QuizesData
import com.crafsed.sas.databinding.FragmentLectureBinding
import com.crafsed.sas.ui.MainActivity
import com.crafsed.sas.ui.anon_questions.AnonQuestionsAdapter
import com.crafsed.sas.ui.quizes.QuizRVAdapter
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FragmentLecture : Fragment(R.layout.fragment_lecture) {
    private var _binding: FragmentLectureBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    private val quizAdapter = QuizRVAdapter(QuizesData.TEST, this::onQuizClicked, false)
    private val anonQuestionsAdapter = AnonQuestionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLectureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAnonQuestions()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupGreenButton()

        setupToolbar()
        setupDescription()
        setupQuizes()
        setupQuestions()

        binding.codeNewBtn.setOnClickListener {
            viewModel.sendCode(binding.codeInputET.text.toString())
            binding.codeInputET.isEnabled = false
            binding.codeNewBtn.isEnabled = false
            binding.codeNewBtn.text = "Проверка..."
            binding.textView2.isEnabled = false
        }

        viewModel.anonQuestions.observe(viewLifecycleOwner) {
            if (it != null) {
                anonQuestionsAdapter.data = it
            }
        }

        binding.codeInputET.setOnFocusChangeListener { v, hasFocus ->
            binding.codeInputET.error = null
        }

        viewModel.sendCodeResult.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.codeNewBtn.text = "Вы отмечены"
                binding.circle.visibility = View.GONE
                binding.progressBar2.visibility = View.GONE
                binding.textView2.visibility = View.GONE
            } else {
                binding.codeInputLayout.error = "Неправильный код"
                binding.codeNewBtn.text = "Отправить"
                binding.codeInputET.isEnabled = true
                binding.codeNewBtn.isEnabled = true
                binding.textView2.isEnabled = true
            }
        }

        viewModel.sendStudentWiFiResult.observe(viewLifecycleOwner) {
            if (it == true) {
                val anim3 = ScaleAnimation(
                    binding.imageView3.scaleX,
                    binding.imageView3.scaleX * 250f,
                    binding.imageView3.scaleY,
                    binding.circle.scaleY * 250f,
                    Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, 0.5f
                )

                anim3.fillAfter = true

                anim3.duration = 1000
                binding.imageView3.startAnimation(anim3)

                binding.textView2.visibility = View.INVISIBLE
                binding.textView3.visibility = View.INVISIBLE
                binding.progressBar2.visibility = View.INVISIBLE

                binding.textView4.visibility = View.VISIBLE
            } else {
                binding.textView2.visibility = View.VISIBLE
                binding.circle.visibility = View.VISIBLE
                binding.progressBar2.visibility = View.INVISIBLE
            }
        }
    }

    fun onQuizClicked(quizesData: QuizesData) {
        viewModel.currentQuiz.value = quizesData
        (requireActivity() as MainActivity).toQuiz()
    }

    private fun setupGreenButton() {
        if (viewModel.isLector) {
            binding.textView2.visibility = View.GONE
            binding.circle.visibility = View.GONE
        } else {
            binding.textView2.setOnClickListener {
                binding.circle.visibility = View.INVISIBLE
                binding.progressBar2.visibility = View.VISIBLE

                val anim = ScaleAnimation(
                    binding.circle.scaleX,
                    binding.circle.scaleX * 0.2f,
                    binding.circle.scaleY,
                    binding.circle.scaleY * 0.2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                    Animation.RELATIVE_TO_SELF, 0.5f
                )

                anim.fillAfter = true
                anim.duration = 1000
                binding.progressBar2.startAnimation(anim)

                val transition: Transition = Fade()

                transition.setDuration(400)
                transition.addTarget(binding.textView2)

                TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
                binding.textView2.visibility = View.INVISIBLE

                val transition2: Transition = Fade()

                transition2.addTarget(binding.textView3)
                transition2.setDuration(1000)
                transition2.setStartDelay(1000)

                TransitionManager.beginDelayedTransition(view as ViewGroup, transition2)
                binding.textView3.visibility = View.VISIBLE

                scanWiFi()
            }
        }
    }

    private fun setupToolbar() {
        viewModel.lecture.observe(viewLifecycleOwner) {
            binding.barTestName.text = it?.obj
            binding.barQuestionNum.text =
                "${it!!.lector}, ${it!!.timeStart} - ${it!!.timeEnd}, ${it!!.place}"
        }
    }

    private fun setupQuizes() {
        binding.testsRV.layoutManager = LinearLayoutManager(context)
        binding.testsRV.adapter = quizAdapter
    }

    private fun setupDescription() {
        viewModel.lecture.observe(viewLifecycleOwner) {
            binding.textView6.text = it!!.description
            if (it.place.contains("LMS", true)) {
                binding.circle.visibility = View.GONE
                binding.textView2.visibility = View.GONE
            }
        }
    }

    private fun setupQuestions() {
        binding.questionsRV.layoutManager = LinearLayoutManager(context)
        binding.questionsRV.adapter = anonQuestionsAdapter

        binding.button2.setOnClickListener {
            (requireActivity() as MainActivity).toQuestions()
        }

        viewModel.anonQuestions.observe(viewLifecycleOwner) {
            if (it != null) {
                anonQuestionsAdapter.data = it
            }
        }

        viewModel.getAnonQuestions()
    }

    private fun transmitWiFi() {
        val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.startLocalOnlyHotspot(
            object : WifiManager.LocalOnlyHotspotCallback() {
                override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation?) {
                    super.onStarted(reservation)
                    val hotspotReservation = reservation
                    val currentConfig = hotspotReservation?.getWifiConfiguration();
                    Log.v(
                        "DANG", "THE PASSWORD IS: "
                                + currentConfig?.preSharedKey
                                + " n SSID is : "
                                + currentConfig?.SSID
                    );
                }

                override fun onFailed(reason: Int) {
                    super.onFailed(reason)
                    println("FAIL $reason")
                }

                override fun onStopped() {
                    super.onStopped()
                    println("STOPPED")
                }
            },
            null
        )
    }

    private fun scanWiFi() {
        val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiScanReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    scanSuccess(wifiManager)
                } else {
                    scanFailure(wifiManager)
                }
            }
        }

        val intentFilter = IntentFilter()

        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        requireContext().registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure(wifiManager)
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanSuccess(wifiManager: WifiManager) {
        Log.e("TAG", "scanSuccess: OK")

        viewModel.sendSSIDS(
            wifiManager.scanResults.map {
                it.SSID
            }
        )
        val results = wifiManager.scanResults.forEach {
            Log.e("TAG", "scanSuccess: $it")
        }
    }


    @SuppressLint("MissingPermission")
    private fun scanFailure(wifiManager: WifiManager) {
        Log.e("TAG", "scanFailure: NOT OK")
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults.forEach {

            Log.e("TAG", "scanSuccess: $it")
        }
    }
}