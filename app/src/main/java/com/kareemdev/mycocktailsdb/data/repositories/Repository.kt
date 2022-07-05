package com.kareemdev.mycocktailsdb.data.repositories

import androidx.lifecycle.LiveData
import com.kareemdev.mycocktailsdb.data.local.CocktailDao
import com.kareemdev.mycocktailsdb.data.model.CocktailList
import com.kareemdev.mycocktailsdb.data.model.DrinkList
import com.kareemdev.mycocktailsdb.data.model.DrinkPreview
import com.kareemdev.mycocktailsdb.data.remote.Api
import com.kareemdev.mycocktailsdb.utils.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(
    private val cocktailDao: CocktailDao,
    private val api: Api
) : IRepository {
    override suspend fun getAlcoholic(): Resource<CocktailList> {
        return handleListCocktailResponse(api.getAllAlcoholicDrinks())
    }

    override suspend fun getNonAlcoholic(): Resource<CocktailList> {
        return handleListCocktailResponse(api.getAllNoAlcoholicDrinks())
    }

    override suspend fun getSearch(searchId: String): Resource<DrinkList> {
        return handleListDrinkResponse(api.searchDrinksById(searchId))
    }

    override suspend fun getSearchByName(searchName: String): Resource<CocktailList> {
        return handleListCocktailResponse(api.searchDrinksByName(searchName))
    }

    override suspend fun searchDrinkByIngredient(searchDrinkIngredient: String): Resource<CocktailList> {
        return handleListCocktailResponse(api.searchDrinksByIngredient(searchDrinkIngredient))
    }

    override fun getSavedCocktails(): LiveData<List<DrinkPreview>> {
        return cocktailDao.getAllCocktail()
    }

    override suspend fun upsert(drink: DrinkPreview) {
        cocktailDao.upsert(drink)
    }

    override suspend fun deleteCocktail(drink: DrinkPreview) {
        cocktailDao.deleteCocktail(drink)
    }

    private fun handleListCocktailResponse(response: Response<CocktailList>): Resource<CocktailList> {
        return try {
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("An unknown error has ocrrured")
            } else {
                return Resource.Error("An unknown error has ocrrured")
            }
        } catch (e: Exception) {
            return Resource.Error(
                "We couldn't reach the server. Check your internet connection",
                null
            )
        }
    }

    private fun handleListDrinkResponse(response: Response<DrinkList>): Resource<DrinkList> {
        return try {
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("An unknown error has ocrrured")
            } else {
                return Resource.Error("An unknown error has ocrrured")
            }
        } catch (e: Exception) {
            return Resource.Error(
                "We couldn't reach the server. Check your internet connection",
                null
            )
        }
    }
}