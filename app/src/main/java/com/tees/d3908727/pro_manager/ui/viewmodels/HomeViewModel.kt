package com.tees.d3908727.pro_manager.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.tees.d3908727.pro_manager.ui.models.Project
import com.tees.d3908727.pro_manager.ui.models.ProjectState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    var userId: String?  = null

    private val _projectState = MutableStateFlow<ProjectState>(ProjectState.Loading)
    val projectState: StateFlow<ProjectState> = _projectState

    val adding = mutableStateOf(false)
    val addedSuccess = mutableStateOf(false)



    init {
        fetchUserProjects()
    }

    private fun fetchUserProjects() {
        userId = firebaseAuth.currentUser?.uid
        Log.d("USER ID", "fetchUserProjects: $userId")
        if (userId == null) {
            _projectState.value = ProjectState.Failure("User not logged in")
            return
        }

        viewModelScope.launch {
            try {
                firebaseFirestore.collection("projects")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        val parseableProjects = snapshot.documents.mapNotNull { document ->
                            val project = document.toObject(Project::class.java)?.copy(id = document.id)
                            project?.makeParseableProject()
                        }
                        _projectState.value = ProjectState.Success(parseableProjects)
                    }
                    .addOnFailureListener { exception ->
                        _projectState.value = ProjectState.Failure(exception.message ?: "Unknown error occurred")
                    }
            }catch (e: Exception){
                _projectState.value = ProjectState.Failure(e.message ?: "Unknown error occurred")
                e.printStackTrace()
            }
        }

    }


}




