package com.example.greenmind.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PlantLocal::class,
        PlantDetailLocal::class
    ],
    version = 2
)
abstract class GreenMindDatabase : RoomDatabase() {

    // Expone el DAO que permite acceder a las tablas de las plantas
    abstract fun plantDao(): IPlantDao

    companion object {
        @Volatile
        private var _instance: GreenMindDatabase? = null

        // Devuelve una única instancia de la base de datos para toda la aplicación
        fun getInstance(context: Context): GreenMindDatabase =
            _instance ?: synchronized(this) {
                _instance ?: buildDatabase(context).also {
                    _instance = it
                }
            }

        // Construye la base de datos local Room
        private fun buildDatabase(context: Context): GreenMindDatabase =
            Room.databaseBuilder(
                context,
                GreenMindDatabase::class.java,
                "greenmind_database"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}