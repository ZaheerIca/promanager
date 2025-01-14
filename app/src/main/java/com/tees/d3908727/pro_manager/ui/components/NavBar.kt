package com.tees.d3908727.pro_manager.ui.components



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.tees.d3908727.pro_manager.ui.models.NavigationItem


@Composable
fun NavBar(navController: NavHostController, selectedItemIndexState: MutableState<Int>) {
    NavigationBar(containerColor = Color.White.copy(0.75f)) {
        navItems.forEachIndexed { index, item ->
            var isSelected = selectedItemIndexState.value == index
            NavigationBarItem(
                selected = isSelected,
                onClick = {

                    selectedItemIndexState.value = index
                    navController.navigate(item.title){
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selectedItemIndexState.value == index) item.onSelectedIcon else item.defaultIcon,
                        contentDescription = item.title,
                        tint = if (isSelected) Color.White else Color.Gray
                    )
                },
                label = {
                    Text(
                        text = item.title.replaceFirstChar { it.uppercase() },
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Transparent,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}



val navItems = listOf<NavigationItem>(

    NavigationItem(
        title = "home",
        onSelectedIcon = Icons.Filled.Home,
        defaultIcon = Icons.Outlined.Home,
    ),

    NavigationItem(
        title = "profile",
        onSelectedIcon = Icons.Filled.AccountBox,
        defaultIcon = Icons.Outlined.AccountBox,
    )

)


