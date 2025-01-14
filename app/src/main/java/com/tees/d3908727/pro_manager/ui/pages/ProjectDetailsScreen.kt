package com.tees.d3908727.pro_manager.ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tees.d3908727.pro_manager.ui.models.ParseableProject
import com.tees.d3908727.pro_manager.ui.viewmodels.DeleteProjectState
import com.tees.d3908727.pro_manager.ui.viewmodels.ProjectViewModel

@Composable
fun ProjectDetailsScreen(
    project: ParseableProject,
    navController: NavController,
    vm: ProjectViewModel
) {
    val deleteState by vm.deleteProjectState.collectAsState()


    if (deleteState is DeleteProjectState.Success) {
        navController.navigate("home") {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Top Bar with Back and Delete buttons
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Back Button
                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = "<- Go Back",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Blue.copy(0.85f)
                    )
                }

                // Delete Button
                TextButton(
                    onClick = { vm.deleteProject(project.id) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    if (deleteState is DeleteProjectState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        Text(
                            text = "Delete Project",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            if (deleteState is DeleteProjectState.Error) {
                val errorMessage = (deleteState as DeleteProjectState.Error).message
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    color = Color.Red,
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display Project Attributes
            ProjectDetailSection("Project Title", project.title)
            ProjectDetailSection("Description", project.description)
            ProjectDetailSection("Start Date", project.startDate)
            ProjectDetailSection("End Date", project.endDate)
            ProjectDetailSection("Is Completed", if (project.isCompleted) "Yes" else "No")

            // Tasks Section
            Text(
                text = "Tasks",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            project.tasks.forEachIndexed { index, task ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${index + 1}. $task",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


@Composable
fun ProjectDetailSection(title: String, value: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = value,
        fontSize = 14.sp,
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.height(18.dp))
}
