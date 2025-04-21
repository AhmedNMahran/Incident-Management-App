package com.github.ahmednmahran.elmtickettracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElmTicketTrackingTheme {
        Greeting("Android")
    }
}