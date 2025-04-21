package com.github.ahmednmahran.elmtickettracking.features.dashboard.data

import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.core.common.Result
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.Count
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.DashboardResponse
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.Incident
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.model.StatusCount
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.model.IncidentStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class DashboardRepositoryImplTest {

    private lateinit var apiService: ApiService
    private lateinit var dashboardRepository: DashboardRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        dashboardRepository = DashboardRepositoryImpl(apiService)
    }

    @Test
    fun `getDashboardStats returns success when API call is successful`() = runTest {
        // Arrange
        val dashboardResponse = DashboardResponse(
            incidents = listOf(
                Incident(status = IncidentStatus.SUBMITTED.code, _count = Count(status = 10)),
                Incident(status = IncidentStatus.IN_PROGRESS.code, _count = Count(status = 5))
            )
        )
        val expectedStatusCounts = listOf(
            StatusCount(status = IncidentStatus.SUBMITTED.code, 10),
            StatusCount(status = IncidentStatus.IN_PROGRESS.code, 5)
        )
        val response = Response.success(dashboardResponse)
        coEvery { apiService.getDashboardStats() } returns response

        // Act
        val result = dashboardRepository.getDashboardStats()

        // Assert
        assert(result is Result.Success)
        val actualStatusCounts = (result as Result.Success).data
        assertEquals(actualStatusCounts, expectedStatusCounts)
    }

    @Test
    fun `getDashboardStats returns error when API call fails`() = runTest {
        // Arrange
        val errorMessage = "Response.error()"
        val errorResponseBody = errorMessage.toResponseBody("text/plain".toMediaTypeOrNull())
        val response = Response.error<DashboardResponse>(404, errorResponseBody)

        coEvery { apiService.getDashboardStats() } returns response

        // Act
        val result = dashboardRepository.getDashboardStats()

        // Assert
        assert(result is Result.Error)
        assertEquals((result as Result.Error).exception.message, errorMessage)
    }

    @Test
    fun `getDashboardStats returns error when an exception is thrown`() = runTest {
        // Arrange
        val expectedException = RuntimeException("Something went wrong")
        coEvery { apiService.getDashboardStats() } throws expectedException

        // Act
        val result = dashboardRepository.getDashboardStats()

        // Assert
        assert(result is Result.Error)
        assertEquals((result as Result.Error).exception, expectedException)
    }

    @Test
    fun `mapToStatusCounts maps DashboardResponse to List of StatusCount correctly`() {
        // Arrange
        val dashboardResponse = DashboardResponse(
            incidents = listOf(
                Incident(status = IncidentStatus.SUBMITTED.code, _count = Count(status = 10)),
                Incident(status = IncidentStatus.IN_PROGRESS.code, _count = Count(status = 5)),
                Incident(status = IncidentStatus.COMPLETED.code, _count = Count(status = 20)),
                Incident(status = IncidentStatus.REJECTED.code, _count = Count(status = 2))
            )
        )
        val expectedStatusCounts = listOf(
            StatusCount(status = IncidentStatus.SUBMITTED.code,10),
            StatusCount(status = IncidentStatus.IN_PROGRESS.code, 5),
            StatusCount(status = IncidentStatus.COMPLETED.code,20),
            StatusCount(status = IncidentStatus.REJECTED.code,2)
        )

        // Act
        val actualStatusCounts = dashboardResponse.mapToStatusCounts()

        // Assert
        assertEquals(actualStatusCounts,expectedStatusCounts)
    }

    @Test
    fun `mapToStatusCounts returns empty list when DashboardResponse incidents list is empty`() {
        // Arrange
        val dashboardResponse = DashboardResponse(incidents = emptyList())
        val expectedStatusCounts = emptyList<StatusCount>()

        // Act
        val actualStatusCounts = dashboardResponse.mapToStatusCounts()

        // Assert
        assertEquals(actualStatusCounts,expectedStatusCounts)
    }
}