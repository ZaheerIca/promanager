package com.tees.d3908727.pro_manager

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tees.d3908727.pro_manager.ui.components.NavBar
import com.tees.d3908727.pro_manager.ui.pages.HomeScreen
import com.tees.d3908727.pro_manager.ui.pages.LogIn
import com.tees.d3908727.pro_manager.ui.pages.Splash
import com.tees.d3908727.pro_manager.ui.pages.Onboard
import com.tees.d3908727.pro_manager.ui.pages.Register
import com.tees.d3908727.pro_manager.ui.viewmodels.LogInViewModel
import com.tees.d3908727.pro_manager.ui.viewmodels.RegisterViewModel
import com.tees.d3908727.pro_manager.ui.viewmodels.SplashViewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.tees.d3908727.pro_manager.ui.models.ParseableProject
import com.tees.d3908727.pro_manager.ui.pages.CreateProjectScreen
import com.tees.d3908727.pro_manager.ui.pages.Profile
import com.tees.d3908727.pro_manager.ui.pages.ProjectDetailsScreen
import com.tees.d3908727.pro_manager.ui.viewmodels.CreateProjectViewModel
import com.tees.d3908727.pro_manager.ui.viewmodels.HomeViewModel
import com.tees.d3908727.pro_manager.ui.viewmodels.ProfileViewModel
import com.tees.d3908727.pro_manager.ui.viewmodels.ProjectViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            ApplicationEntryPoint()
        }
    }
}


@Composable
fun ApplicationEntryPoint(){
    val navController = rememberNavController()
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = true)
    var currentRoute by remember { mutableStateOf("splash") }
    val bottomBarIndexState = rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener {_, destination, _ ->
            currentRoute = destination.route!!
        }
    }


    Scaffold(
        bottomBar = {
            if (currentRoute in listOf("home", "profile")){
                systemUiController.setNavigationBarColor(color = Color.Black.copy(0.75f))
                Box(modifier = Modifier.background(Color.Black).padding(vertical = 0.dp)){
                    NavBar(navController = navController, bottomBarIndexState)
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = "splash"
        ){

            composable("splash") {
                val splashVM = SplashViewModel(context)
                Splash(navController,splashVM)
            }

            composable("onboard") { Onboard(navController) }

            composable("register") {
                val vm = RegisterViewModel()
                Register(navController, vm)
            }

            composable("login") {
                val vm = LogInViewModel()
                LogIn(navController, vm)
            }

            composable("home") {
                bottomBarIndexState.value = 0
                val homeViewModel: HomeViewModel = viewModel()
                HomeScreen(navController, homeViewModel)
            }

            composable("profile"){
                bottomBarIndexState.value = 1
                val profileViewModel: ProfileViewModel = viewModel()
                Profile(navController, profileViewModel)
            }

            composable("create"){
                val vm: CreateProjectViewModel = viewModel()
                CreateProjectScreen(navController, vm)
            }

            composable(
                route = "project/{project}",
                arguments = listOf(
                    navArgument("project") { type = NavType.StringType }
                )
            ){ backStackEntry ->
                val vm: ProjectViewModel = viewModel()
                val projectJson = backStackEntry.arguments?.getString("project")
                val project = Gson().fromJson(projectJson, ParseableProject::class.java)
                ProjectDetailsScreen(project, navController, vm)
            }
        }
    }

}