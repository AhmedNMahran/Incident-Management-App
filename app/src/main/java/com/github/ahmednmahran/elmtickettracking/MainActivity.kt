package com.github.ahmednmahran.elmtickettracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.github.ahmednmahran.elmtickettracking.core.designsystem.theme.ElmTicketTrackingTheme
import com.github.ahmednmahran.elmtickettracking.core.navigation.AppNavHost
import com.github.ahmednmahran.elmtickettracking.core.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElmTicketTrackingTheme {
                val navController = rememberNavController()
                val isLoggedIn = remember { mutableStateOf(false) } // Would check auth state

                AppNavHost(
                    navController = navController,
                    startDestination = if (isLoggedIn.value) Screens.Dashboard.route
                    else Screens.Login.route
                )
            }
        }
    }
}