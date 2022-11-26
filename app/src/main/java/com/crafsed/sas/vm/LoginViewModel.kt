package com.crafsed.sas.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crafsed.sas.network.ServerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    val repo = ServerRepo()

    val isLoggedIn = MutableLiveData<Boolean?>(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repo.login(email, password)
            }

            isLoggedIn.value = response.first!=null && response.second!=null
        }
    }
}