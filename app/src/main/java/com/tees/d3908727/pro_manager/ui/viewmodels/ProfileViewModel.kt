package com.tees.d3908727.pro_manager.ui.viewmodels

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel: ViewModel(){

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // State to hold the profile fetching status
    var profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            profileState.value = ProfileState.Error("User is not logged in.")
            return
        }

        val userId = currentUser.uid
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userProfile = User(
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                    )
                    profileState.value = ProfileState.Success(userProfile)
                } else {
                    profileState.value = ProfileState.Error("User profile not found.")
                }
            }
            .addOnFailureListener { exception ->
                profileState.value = ProfileState.Error("Failed to fetch user profile: ${exception.message}")
            }
    }


    fun logout() {
        viewModelScope.launch {
            profileState.value = ProfileState.LoggingOut
            try {
                auth.signOut()
                profileState.value = ProfileState.LoggedOut
            } catch (e: Exception) {
                profileState.value = ProfileState.Error("Logout failed: ${e.message}")
            }
        }
    }
}


data class User(
    val name: String = "",
    val email: String = "",
)

sealed class ProfileState {
    object Loading : ProfileState()
    object LoggingOut : ProfileState()
    data class Success(val userProfile: User) : ProfileState()
    data class Error(val message: String) : ProfileState()
    object LoggedOut : ProfileState()
}