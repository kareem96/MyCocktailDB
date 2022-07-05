package com.kareemdev.mycocktailsdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kareemdev.mycocktailsdb.data.model.DrinkPreview

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(drink: DrinkPreview)

    @Query("SELECT * FROM cocktails")
    fun getAllCocktail(): LiveData<List<DrinkPreview>>

    @Delete
    suspend fun deleteCocktail(drink: DrinkPreview)
}