package com.crafsed.sas.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crafsed.sas.data.*
import com.crafsed.sas.network.ServerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    var token: String = ""
    var currentQuiz = MutableLiveData<QuizesData>()
    val currentQuizResult = ArrayList<List<String>>()

    var bsExpand: Boolean = false
    val schedule = MutableLiveData<ListData?>()
    val lecture = MutableLiveData<PairDescription?>()
    val codeResponse = MutableLiveData<Boolean>()
    var isLector: Boolean = false
    val sendCodeResult = MutableLiveData<Boolean?>()
    val sendStudentWiFiResult = MutableLiveData<Boolean?>()
    val sendPrepodSsidResult = MutableLiveData<Boolean?>()
    val anonQuestions = MutableLiveData<List<AnonQuestionData>?>()

    val repo = ServerRepo()
    fun getSchedule() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.getSchedule(token)
            }
            if (response != null) {
                schedule.value = response
            }
        }
    }

    fun getAnonQuestions() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.getQuestions(token, "1")
            }
            anonQuestions.value = response
        }
    }

    fun sendAnonQuestion(header: String, text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.askQuestion(token, "1", "${header}&${text}")
            }
        }
    }

    fun sendCode(text: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.codeCheckIn(token, "1", text)
            }
            sendCodeResult.value = response
        }
    }

    fun postQuizResult(currentQuizResult: java.util.ArrayList<List<String>>, id: String) {
//
    }

    fun sendSSIDS(ssid: List<String?>) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.studentWiFi(token, "1", ssid.map { it.orEmpty() })
            }
            sendStudentWiFiResult.value = response
        }
    }

    fun setSSID(ssid: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.prepodWiFi(token, "1", ssid)
            }
            sendPrepodSsidResult.value = response
        }
    }

    fun openQuiz(quizesData: QuizesData) {
///./
    }

    fun sendNewCode(toString: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.setCode(token, "1", toString)
            }
        }
    }

}