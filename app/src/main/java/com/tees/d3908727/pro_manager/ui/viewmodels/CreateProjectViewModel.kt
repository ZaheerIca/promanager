package com.tees.d3908727.pro_manager.ui.viewmodels

import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.tees.d3908727.pro_manager.ui.models.ParseableProject
import com.tees.d3908727.pro_manager.ui.models.Project
import com.tees.d3908727.pro_manager.ui.models.ProjectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateProjectViewModel: ViewModel(){

    private val firestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()

    var userId: String?  = null

    private val _projectState = MutableStateFlow< PState>(PState.Idle)
    val projectState: StateFlow<PState> = _projectState

    val ladingState = mutableStateOf(false)


    fun addProject(project: Project) {
        ladingState.value = true
        _projectState.value = PState.Loading

        val userId = firebaseAuth.uid
        if (userId == null){
            _projectState.value = PState.AddError("No User Error")
            ladingState.value = false
            return
        }

        viewModelScope.launch {
            try {
                val projectToAdd = project.copy(userId = userId)
                val collectionRef = firestore.collection("projects")
                collectionRef.add(projectToAdd)
                    .addOnSuccessListener { documentReference ->
                         _projectState.value = PState.Success
                        ladingState.value = false
                    }
                    .addOnFailureListener { e ->
                        ladingState.value = false
                        _projectState.value = PState.AddError(e.message ?: "Failed to add project")
                    }

            }catch (e: Exception){
                e.printStackTrace()
                ladingState.value = false
                _projectState.value = PState.AddError(e.message ?: "Failed to add project")
            }
        }
    }
}


sealed class PState {
    object Loading : PState()
    object Idle : PState()
    object Success: PState()
    data class AddError(val error: String) : PState()
}