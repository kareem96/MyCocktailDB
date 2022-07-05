package com.kareemdev.mycocktailsdb.data.remote

import com.kareemdev.mycocktailsdb.data.model.CocktailList
import com.kareemdev.mycocktailsdb.data.model.DrinkList
import com.kareemdev.mycocktailsdb.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("api/json/v1/1/filter.php?a=Alcoholic")
    suspend fun getAllAlcoholicDrinks(
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<CocktailList>

    @GET("api/json/v1/1/filter.php?a=Non_Alcoholic")
    suspend fun getAllNoAlcoholicDrinks(
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<CocktailList>

    @GET("api/json/v1/1/lookup.php")
    suspend fun searchDrinksById(
        @Query("i")
        searchDrink: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<DrinkList>

    @GET("api/json/v1/1/search.php")
    suspend fun searchDrinksByName(
        @Query("s")
        searchDrink: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<CocktailList>

    @GET("api/json/v1/1/filter.php")
    suspend fun searchDrinksByIngredient(
        @Query("i")
        searchDrink: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<CocktailList>

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomDrink(
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<DrinkList>
}