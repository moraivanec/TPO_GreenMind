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

    abstract fun plantDao(): IPlantDao

    companion object {
        @Volatile
        private var _instance: GreenMindDatabase? = null

        fun getInstance(context: Context): GreenMindDatabase =
            _instance ?: synchronized(this) {
                _instance ?: buildDatabase(context).also {
                    _instance = it
                }
            }

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