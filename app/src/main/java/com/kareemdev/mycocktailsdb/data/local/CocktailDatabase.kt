package com.kareemdev.mycocktailsdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kareemdev.mycocktailsdb.data.model.DrinkPreview


@Database(entities = [DrinkPreview::class], version = 1)

abstract class CocktailDatabase : RoomDatabase() {
    abstract fun getCocktailDao(): CocktailDao

    companion object {
        @Volatile
        private var instance: CocktailDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CocktailDatabase::class.java,
            "cocktail.db"
        ).build()
    }
}