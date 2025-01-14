package com.tees.d3908727.pro_manager.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow


class ProjectViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    // State to manage project deletion
    var deleteProjectState = MutableStateFlow<DeleteProjectState>(DeleteProjectState.Idle)


    fun deleteProject(projectId: String) {
        deleteProjectState.value = DeleteProjectState.Loading

        firestore.collection("projects").document(projectId)
            .delete()
            .addOnSuccessListener {
                deleteProjectState.value = DeleteProjectState.Success
            }
            .addOnFailureListener { exception ->
                deleteProjectState.value = DeleteProjectState.Error(
                    message = "Failed to delete project: ${exception.message}"
                )
            }
    }
}


sealed class DeleteProjectState {
    object Idle : DeleteProjectState()
    object Loading : DeleteProjectState()
    object Success : DeleteProjectState()
    data class Error(val message: String) : DeleteProjectState()
}
