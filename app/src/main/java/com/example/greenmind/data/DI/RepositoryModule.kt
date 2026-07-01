package com.example.greenmind.data.DI

import com.example.greenmind.data.FirebaseAuthRepository
import com.example.greenmind.data.FirestoreGardenRepository
import com.example.greenmind.data.GeminiRepository
import com.example.greenmind.data.IPlantDataSource
import com.example.greenmind.data.PlantApiDataSource
import com.example.greenmind.data.PlantRepository
import com.example.greenmind.domain.IAuthRepository
import com.example.greenmind.domain.IGardenRemoteRepository
import com.example.greenmind.domain.IGeminiRepository
import com.example.greenmind.domain.IPlantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlantRepository(
        plantRepository: PlantRepository
    ): IPlantRepository

    @Binds
    @Singleton
    abstract fun bindPlantDataSource(
        plantApiDataSource: PlantApiDataSource
    ): IPlantDataSource

    @Binds
    @Singleton
    abstract fun bindGardenRemoteRepository(
        firestoreGardenRepository: FirestoreGardenRepository
    ): IGardenRemoteRepository

    @Binds
    @Singleton
    abstract fun bindGeminiRepository(
        geminiRepository: GeminiRepository
    ): IGeminiRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        firebaseAuthRepository: FirebaseAuthRepository
    ): IAuthRepository
}