package com.github.ahmednmahran.elmtickettracking.core.data.remote

import com.github.ahmednmahran.elmtickettracking.core.data.model.IncidentType
import com.github.ahmednmahran.elmtickettracking.features.auth.data.model.LoginResponse
import com.github.ahmednmahran.elmtickettracking.features.create.data.model.CreateIncidentRequest
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.DashboardResponse
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.IncidentResponse
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.Incident
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.UpdateIncidentRequest
import com.github.ahmednmahran.elmtickettracking.features.tracking.data.model.TrackingData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: Map<String, String>): Response<Unit>

    @POST("verify-otp")
    suspend fun verifyOtp(@Body request: Map<String, String>): Response<LoginResponse>

    @GET("types")
    suspend fun getIncidentTypes(): Response<List<IncidentType>>

    @GET("incident")
    suspend fun getIncidents(@Query("startDate") startDate: String? = null): Response<IncidentResponse>

    @POST("incident")
    suspend fun submitIncident(@Body incident: CreateIncidentRequest): Response<IncidentResponse>

    @PUT("incident/change-status")
    suspend fun changeIncidentStatus(@Body request: UpdateIncidentRequest): Response<Incident>

    @POST("buses/track-bus")
    suspend fun trackWorker(@Body trackingData: List<TrackingData>): Response<List<TrackingData>>

    @GET("buses/track-bus")
    suspend fun getTrackingData(): Response<List<TrackingData>>

    @GET("dashboard")
    suspend fun getDashboardStats(): Response<DashboardResponse>
}