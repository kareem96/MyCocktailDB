package com.kareemdev.mycocktailsdb.presentation.ui.fragment.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.mycocktailsdb.data.model.CocktailList
import com.kareemdev.mycocktailsdb.data.repositories.Repository
import com.kareemdev.mycocktailsdb.utils.Constants.Companion.searchName
import com.kareemdev.mycocktailsdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){
    var searchCocktail: MutableLiveData<Resource<CocktailList>> = MutableLiveData()

    fun searchCocktail(searchQuery: String, searchType: String) = viewModelScope.launch {
        searchCocktail.postValue(Resource.Loading())
        val response = if(searchType == searchName){
            repository.getSearchByName(searchQuery)
        }else{
            repository.searchDrinkByIngredient(searchQuery)
        }
        searchCocktail.postValue(response)
    }
}