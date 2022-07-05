package com.kareemdev.mycocktailsdb.data.repositories

import androidx.lifecycle.LiveData
import com.kareemdev.mycocktailsdb.data.model.CocktailList
import com.kareemdev.mycocktailsdb.data.model.DrinkList
import com.kareemdev.mycocktailsdb.data.model.DrinkPreview
import com.kareemdev.mycocktailsdb.utils.Resource

interface IRepository {
    suspend fun getAlcoholic(): Resource<CocktailList>

    suspend fun getNonAlcoholic(): Resource<CocktailList>

    suspend fun getGlass(): Resource<CocktailList>

    suspend fun getSearch(searchId: String):Resource<DrinkList>

    suspend fun getSearchByName(searchName: String):Resource<CocktailList>

    suspend fun searchDrinkByIngredient(searchDrinkIngredient: String): Resource<CocktailList>

    fun getSavedCocktails(): LiveData<List<DrinkPreview>>

    suspend fun upsert(drink: DrinkPreview)

    suspend fun deleteCocktail(drink: DrinkPreview)

}