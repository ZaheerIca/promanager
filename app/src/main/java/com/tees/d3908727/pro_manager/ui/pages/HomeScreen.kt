package com.tees.d3908727.pro_manager.ui.pages


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
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
import androidx.navigation.NavController
import com.google.gson.Gson
import com.tees.d3908727.pro_manager.ui.components.ProjectItemCard
import com.tees.d3908727.pro_manager.ui.models.ProjectState
import com.tees.d3908727.pro_manager.ui.viewmodels.HomeViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    val projectState by homeViewModel.projectState.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(20.dp)
                    .background(MaterialTheme.colorScheme.surface),
                horizontalArrangement = Arrangement.End

            ) {
                TextButton(
                    onClick = {
                        navController.navigate("create"){
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add", tint = Color.Blue)
                    Text(text = "Add Project", color = Color.Blue.copy(0.85f))
                }

            }


            if (projectState is ProjectState.Loading){
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 4.dp
                    )
                }

            } else if (projectState is ProjectState.Success) {
                val projects = (projectState as ProjectState.Success).projects

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 1.dp)
                ) {
                    items(projects){ project ->
                        ProjectItemCard(
                            project,
                            onClick = {
                                val projectJson = Uri.encode(Gson().toJson(project))
                                navController.navigate("project/$projectJson")
                           }
                        )
                    }
                }
            }

        }
    }

}


