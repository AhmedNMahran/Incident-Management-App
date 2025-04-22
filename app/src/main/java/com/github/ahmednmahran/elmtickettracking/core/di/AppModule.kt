package com.github.ahmednmahran.elmtickettracking.core.di

import android.content.Context
import com.github.ahmednmahran.elmtickettracking.core.data.local.SharedPrefsManager
import com.github.ahmednmahran.elmtickettracking.core.data.remote.ApiService
import com.github.ahmednmahran.elmtickettracking.core.data.remote.RetrofitClient
import com.github.ahmednmahran.elmtickettracking.features.auth.domain.AuthRepository
import com.github.ahmednmahran.elmtickettracking.features.auth.data.AuthRepositoryImpl
import com.github.ahmednmahran.elmtickettracking.features.create.data.CreateIncidentRepositoryImpl
import com.github.ahmednmahran.elmtickettracking.features.create.domain.CreateIncidentRepository
import com.github.ahmednmahran.elmtickettracking.features.dashboard.domain.DashboardRepository
import com.github.ahmednmahran.elmtickettracking.features.dashboard.data.DashboardRepositoryImpl
import com.github.ahmednmahran.elmtickettracking.features.incidents.domain.IncidentDetailRepository
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.IncidentDetailRepositoryImpl
import com.github.ahmednmahran.elmtickettracking.features.incidents.domain.IncidentListRepository
import com.github.ahmednmahran.elmtickettracking.features.incidents.data.IncidentListRepositoryImpl
import com.github.ahmednmahran.elmtickettracking.features.tracking.domain.TrackingRepository
import com.github.ahmednmahran.elmtickettracking.features.tracking.data.TrackingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService = RetrofitClient.apiService

    @Provides
    @Singleton
    fun provideSharedPrefsManager(@ApplicationContext context: Context): SharedPrefsManager =
        SharedPrefsManager(context)

    @Provides
    @Singleton
    fun provideAuthRepository(api: ApiService, prefs: SharedPrefsManager): AuthRepository =
        AuthRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideDashboardRepository(api: ApiService): DashboardRepository =
        DashboardRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideIncidentListRepository(api: ApiService): IncidentListRepository =
        IncidentListRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideIncidentDetailRepository(api: ApiService): IncidentDetailRepository =
        IncidentDetailRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideCreateIncident(api: ApiService): CreateIncidentRepository =
        CreateIncidentRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTrackingRepository(api: ApiService): TrackingRepository =
        TrackingRepositoryImpl(api)
}