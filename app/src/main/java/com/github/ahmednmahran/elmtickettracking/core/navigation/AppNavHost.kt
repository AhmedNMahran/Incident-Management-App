package com.github.ahmednmahran.elmtickettracking.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.ahmednmahran.elmtickettracking.features.auth.login.ui.LoginScreen
import com.github.ahmednmahran.elmtickettracking.features.auth.otp.ui.OtpScreen
import com.github.ahmednmahran.elmtickettracking.features.dashboard.ui.DashboardScreen
import com.github.ahmednmahran.elmtickettracking.features.incidents.ui.IncidentDetailScreen
import com.github.ahmednmahran.elmtickettracking.features.incidents.ui.IncidentListScreen
import com.github.ahmednmahran.elmtickettracking.features.tracking.ui.TrackingScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screens.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Login.route) {
            LoginScreen{ email -> navController.navigate(Screens.Otp.createRoute(email)) }

        }

        composable(
            route = Screens.Otp.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OtpScreen(
                email = email,
                onVerificationSuccess = { navController.navigate(Screens.Dashboard.route) }
            )
        }
        
        composable(Screens.Dashboard.route) {
            DashboardScreen(
                onIncidentsClick = { navController.navigate(Screens.IncidentList.route) },
                onTrackingClick = { navController.navigate(Screens.Tracking.route) }
            )
        }
        
        composable(Screens.IncidentList.route) {
            IncidentListScreen(
                onIncidentClick = { id ->
                    navController.navigate(Screens.IncidentDetail.createRoute(id))
                },
                onFilterClick = { /* Handle filter */ },
                onAddClick = {
                    //currently we show the new incident dialog instead of navigating to the new incident screen
                    navController.navigate(Screens.NewIncident.route) }
            )
        }
        
        composable(
            route = Screens.IncidentDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val incidentId = backStackEntry.arguments?.getString("id") ?: ""
            IncidentDetailScreen(
                incidentId = incidentId,
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(Screens.Tracking.route) {
            TrackingScreen()
        }
    }
}