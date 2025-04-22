package com.github.ahmednmahran.elmtickettracking.core.navigation

import java.net.URLEncoder

sealed class Screens(val route: String) {
    object Login : Screens("login")
    object Otp : Screens("otp/{email}") {
        fun createRoute(email: String) = "otp/${URLEncoder.encode(email, "UTF-8")}"
    }
    object Dashboard : Screens("dashboard")
    object IncidentList : Screens("incidents")
    object IncidentDetail : Screens("incidents/{id}") {
        fun createRoute(id: String) = "incidents/$id"
    }
    object NewIncident : Screens("incidents/new") // add a new incident screen to the project and use this to navigate
    object Tracking : Screens("tracking")
}