package com.example.greenmind.data.DI

import android.content.Context
import com.example.greenmind.data.IPlantAPI
import com.example.greenmind.data.local.GreenMindDatabase
import com.example.greenmind.data.local.IPlantDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkAndDatabaseModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://perenual.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePlantAPI(retrofit: Retrofit): IPlantAPI =
        retrofit.create(IPlantAPI::class.java)

    @Provides
    @Singleton
    fun provideGreenMindDatabase(
        @ApplicationContext context: Context
    ): GreenMindDatabase =
        GreenMindDatabase.getInstance(context)

    @Provides
    fun providePlantDao(
        greenMindDatabase: GreenMindDatabase
    ): IPlantDao =
        greenMindDatabase.plantDao()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore =
        FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()
}