package com.tees.d3908727.pro_manager.ui.models

import androidx.compose.ui.graphics.vector.ImageVector
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.String


data class Project(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val isCompleted: Boolean,
    val tasks: List<String>
) {
    constructor() : this("", "", "", "", "", "", false, emptyList())

    fun makeParseableProject(): ParseableProject{
        return ParseableProject(
            id = id,
            userId = userId,
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            isCompleted = isCompleted,
            tasks = tasks
        )
    }
}


@Parcelize
data class ParseableProject(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val isCompleted: Boolean,
    val tasks: List<String>
): Parcelable {
    fun makeProject(): Project {
        return Project(
            id = id,
            userId = userId,
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            isCompleted = isCompleted,
            tasks = tasks
        )
    }
}


data class NavigationItem(
    val title: String,
    val onSelectedIcon: ImageVector,
    val defaultIcon: ImageVector,
)

sealed class ProjectState {
    object Loading : ProjectState()
    data class AddError(val error: String) : ProjectState()
    data class Success(val projects: List<ParseableProject>) : ProjectState()
    data class Failure(val error: String) : ProjectState()
}

