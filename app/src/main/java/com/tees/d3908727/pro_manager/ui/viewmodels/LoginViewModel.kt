package com.tees.d3908727.pro_manager.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

class LogInViewModel: ViewModel(){

    val firebaseAuth = FirebaseAuth.getInstance()
    var emailState = mutableStateOf("")
    var passwordState = mutableStateOf("")

    var logInError = MutableStateFlow("")
    var logInSuccess = MutableStateFlow(false)
    var loadingState = mutableStateOf(false)


    fun clearErrorMessage() {
        logInError.value = ""
        logInSuccess.value = false
    }

    fun logIn(){

        if (emailState.value.length < 5){
            logInError.value = "Email does not match email format"
            return
        }

        if (passwordState.value.length < 8){
            logInError.value = "Password must be at least 8 characters long"
            return
        }

        loadingState.value = true

        firebaseAuth.signInWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener{task ->
                if (!task.isSuccessful){
                    logInError.value = task.exception?.message!!
                    loadingState.value = false
                    return@addOnCompleteListener

                }else{
                    logInSuccess.value = true
                }
            }

    }

}