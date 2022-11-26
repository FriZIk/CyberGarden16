package com.crafsed.sas.vm

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crafsed.sas.data.AnonQuestionData

class MainViewModel : ViewModel() {
    val codeResponse = MutableLiveData<Boolean>()

    val anonQuestions = MutableLiveData(AnonQuestionData.TEST)

    fun getAnonQuestions() {

    }

    fun sendAnonQuestion(header: String, text: String) {

    }

    fun sendCode(text: String) {
        TODO("Not yet implemented")
    }

}