package com.nebiozkan.akrepindirici.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nebiozkan.akrepindirici.ui.components.AkrepBottomNavBar
import com.nebiozkan.akrepindirici.ui.screens.home.HomeScreen
import com.nebiozkan.akrepindirici.ui.screens.instagram.InstagramScreen
import com.nebiozkan.akrepindirici.ui.screens.settings.SettingsScreen
import com.nebiozkan.akrepindirici.ui.screens.tiktok.TikTokScreen

@Composable
fun AkrepNavHost(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            AkrepBottomNavBar(
                currentRoute = currentRoute,
                onNavigate = { destination ->
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Home.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Destination.Home.route) { HomeScreen() }
            composable(Destination.TikTok.route) { TikTokScreen() }
            composable(Destination.Instagram.route) { InstagramScreen() }
            composable(Destination.Settings.route) { SettingsScreen() }
        }
    }
}
