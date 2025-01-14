package com.tees.d3908727.pro_manager.ui.viewmodels


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel: ViewModel(){

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseFireStore = FirebaseFirestore.getInstance()
    var nameState = mutableStateOf("")
    var emailState = mutableStateOf("")
    var passwordState = mutableStateOf("")
    var confirmPasswordState = mutableStateOf("")

    var registerError = MutableStateFlow("")
    var registerSuccess = MutableStateFlow(false)
    var loadingState = mutableStateOf(false)


    fun clearErrorMessage() {
        registerError.value = ""
        registerSuccess.value = false
    }

    fun registerAccount() {
        if (nameState.value.length < 4) {
            registerError.value = "Name must be at least 5-characters long"
            return
        }

        if (emailState.value.length < 5){
            registerError.value = "Email does not match email format"
            return
        }

        if (passwordState.value.length < 8){
            registerError.value = "Password must be at least 8 characters long"
            return
        }

        if (confirmPasswordState.value != passwordState.value){
            registerError.value = "Confirm passwords did not not match password"
            return
        }

        loadingState.value = true

        firebaseAuth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener{ task ->
                if (!task.isSuccessful){
                    registerError.value = task.exception?.message!!
                    loadingState.value = false
                    return@addOnCompleteListener
                }else{
                    val userId = firebaseAuth.currentUser?.uid

                    val userData = mapOf(
                        "name" to nameState.value,
                        "email" to emailState.value,
                    )

                    val fireStoreBatch = firebaseFireStore.batch()
                    val documentReference = firebaseFireStore.collection("users").document(userId!!)
                    fireStoreBatch.set(documentReference, userData)

                    fireStoreBatch.commit().addOnCompleteListener{ result ->
                        if (!result.isSuccessful){
                            Log.d("CreateAccount", "Error Creating User: ${result.exception!!.message}")
                            loadingState.value = false
                        }
                    }

                    registerSuccess.value = true
                }


            }
    }


}