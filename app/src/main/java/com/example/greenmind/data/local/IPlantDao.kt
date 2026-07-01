package com.example.greenmind.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IPlantDao {

    @Query("SELECT * FROM plants")
    suspend fun getPlants(): List<PlantLocal>

    @Query("SELECT * FROM plants WHERE id = :id LIMIT 1")
    suspend fun getPlantById(id: Int): PlantLocal?

    // Inserta una planta en la tabla plants
    // Si ya existe, la reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: PlantLocal)

    // Inserta varias plantas en la tabla plants
    // Si alguna ya existe, la reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlants(plants: List<PlantLocal>)

    @Delete
    suspend fun deletePlant(plant: PlantLocal)


    // Mi Jardín
    // Obtiene las plantas guardadas en Mi Jardin, ordenadas por fecha de guardado
    @Query("SELECT * FROM plant_details ORDER BY dateAdded DESC")
    suspend fun getSavedPlants(): List<PlantDetailLocal>

    @Query("SELECT * FROM plant_details WHERE id = :id LIMIT 1")
    suspend fun getSavedPlantById(id: Int): PlantDetailLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedPlant(plantDetail: PlantDetailLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedPlants(plants: List<PlantDetailLocal>)

    @Query("DELETE FROM plant_details WHERE id = :id")
    suspend fun deleteSavedPlantById(id: Int)
}