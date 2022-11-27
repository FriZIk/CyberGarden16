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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crafsed.sas.R
import com.crafsed.sas.data.QuizesData
import com.crafsed.sas.databinding.FragmentLecturePrepBinding
import com.crafsed.sas.ui.anon_questions.AnonQuestionsAdapter
import com.crafsed.sas.ui.quizes.QuizRVAdapter
import com.crafsed.sas.vm.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.random.Random

class FragmentLecturePrepod : Fragment(R.layout.fragment_lecture_prep) {
    private var _binding: FragmentLecturePrepBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by sharedViewModel()

    private val quizAdapter = QuizRVAdapter(QuizesData.TEST, this::onQuizClicked, true)
    private val anonQuestionsAdapter = AnonQuestionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLecturePrepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupDescription()
        setupQuizes()
        setupQuestions()

        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                transmitWiFi()
            }
        }

        binding.codeNewBtn.setOnClickListener {
            binding.textView5.text = Random.nextInt(100000, 999999).toString()
            viewModel.sendNewCode(binding.textView5.text.toString())
        }
    }

    fun onQuizClicked(quizesData: QuizesData) {
        viewModel.openQuiz(quizesData)
    }

    private fun setupToolbar() {
        viewModel.lecture.observe(viewLifecycleOwner) {
            binding.barTestName.text = it!!.obj
            binding.barQuestionNum.text =
                "${it!!.lector}, ${it.timeStart} - ${it.timeEnd}, ${it.place}"
        }
    }

    private fun setupQuizes() {
        binding.testsRV.layoutManager = LinearLayoutManager(context)
        binding.testsRV.adapter = quizAdapter
    }

    private fun setupDescription() {
        viewModel.lecture.observe(viewLifecycleOwner) {
            binding.textView6.text = it!!.description
        }
    }

    private fun setupQuestions() {
        binding.questionsRV.layoutManager = LinearLayoutManager(context)
        binding.questionsRV.adapter = anonQuestionsAdapter

        viewModel.anonQuestions.observe(viewLifecycleOwner) {
            if (it != null) {
                anonQuestionsAdapter.data = it
            }
        }
    }

    var reservation: WifiManager.LocalOnlyHotspotReservation? = null

    private fun stopTransmittinWiFi() {
        reservation?.close()
        viewModel.setSSID("")
    }

    private fun transmitWiFi() {
        val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiManager.startLocalOnlyHotspot(
            object : WifiManager.LocalOnlyHotspotCallback() {
                override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation?) {
                    super.onStarted(reservation)
                    val hotspotReservation = reservation
                    val currentConfig = hotspotReservation?.getWifiConfiguration();
                    Log.v("DANG", "THE PASSWORD IS: "
                            + currentConfig?.preSharedKey
                            + " n SSID is : "
                            + currentConfig?.SSID);
                    viewModel.setSSID(currentConfig?.SSID.orEmpty())
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