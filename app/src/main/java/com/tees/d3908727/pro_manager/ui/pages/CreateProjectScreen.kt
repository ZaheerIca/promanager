package com.tees.d3908727.pro_manager.ui.pages

import com.tees.d3908727.pro_manager.ui.components.CustomButton
import com.tees.d3908727.pro_manager.ui.components.CustomDatePicker
import com.tees.d3908727.pro_manager.ui.components.CustomField
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.tees.d3908727.pro_manager.ui.models.NavigationItem
import com.tees.d3908727.pro_manager.ui.models.Project
import com.tees.d3908727.pro_manager.ui.models.ProjectState
import com.tees.d3908727.pro_manager.ui.viewmodels.CreateProjectViewModel
import com.tees.d3908727.pro_manager.ui.viewmodels.PState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

@Composable
fun CreateProjectScreen(
    navController: NavController,
    createViewModel: CreateProjectViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf(Date()) }
    var tasks by remember { mutableStateOf(listOf<String>()) }
    val taskState = remember { mutableStateOf("") }
    val maxTasks = 8

    val context = LocalContext.current
    val state by createViewModel.projectState.collectAsState()

    if (state is PState.Success){
        navController.navigate("home"){
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
        }
    }

    // Function to add task
    fun addTask(task: String) {
        if (tasks.size < maxTasks && task.length >= 7) {
            tasks = tasks + task
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Back Button
                TextButton(
                    onClick = {
                        navController.navigate("home"){
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = "<- Go Back",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Blue.copy(0.85f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Create a Project",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Project Title
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Project Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Project Description
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                maxLines = 5,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { })
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Start Date Picker
            CustomDatePicker(label = "Start Date", selectedDate = startDate) {
                startDate = it
            }

            Spacer(modifier = Modifier.height(4.dp))

            // End Date Picker
            CustomDatePicker(label = "End Date", selectedDate = endDate) {
                endDate = it
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Tasks Section
            Column(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Project Tasks:",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                tasks.forEachIndexed { index, task ->
                    Text(text = "${index + 1}. $task" , modifier = Modifier.padding(start = 16.dp))
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Spacer(modifier = Modifier.height(4.dp))

                if (tasks.size < maxTasks) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        CustomField(
                            state = taskState,
                            label = "Task",
                            maxLine = 3,
                            leadingIcon = null,
                            emitFinalValue = {it -> taskState.value = it }
                        )
                    }
                }

                TextButton(
                    onClick = {
                        addTask(taskState.value)
                        taskState.value = ""
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ){
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
                        Text(text = "Add Task", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            CustomButton(
                loadingState = createViewModel.ladingState,
                text = "Create Project",

                handleClick = {
                    if (tasks.isEmpty()){
                        Toast.makeText(context, "You must add at least one task", Toast.LENGTH_SHORT).show()
                    }else{
                        if (title.isNotBlank() && description.isNotBlank()) {
                            val project = Project(
                                "", "", title,description,startDate.toString(),
                                endDate.toString(), false, tasks)

                            createViewModel.addProject(project)
                        } else {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )

        }
    }

}



@Preview
@Composable
fun ProjectCreationModalPreview() {

}
