package com.tees.d3908727.pro_manager.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(context: Context): ViewModel(){

    val firebase = FirebaseAuth.getInstance()

    val nextRoute = MutableStateFlow("")


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("pro-manager", Context.MODE_PRIVATE)

    var canNavigate = MutableStateFlow(false)


    init {
        startTimer()
    }


    fun startTimer() {

        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                viewModelScope.launch {

                    val isFirstTime = sharedPreferences.getString("hasOnBoard", "") ?: ""
                    if (isFirstTime.isEmpty()){
                        nextRoute.value = "onboard"
                        canNavigate.value = true

                        sharedPreferences.edit().putString("hasOnBoard", "yes").apply()
                    }else {
                        // Check if the user has logged In
                        if (firebase.currentUser == null){
                            nextRoute.value = "login"
                            canNavigate.value = true
                        }else {
                            nextRoute.value = "home"
                            canNavigate.value = true
                        }

                    }

                }
            }
        }
        timer.start()
    }


}