package com.kareemdev.mycocktailsdb.presentation.ui.fragment.alcoholic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.mycocktailsdb.data.model.CocktailList
import com.kareemdev.mycocktailsdb.data.repositories.Repository
import com.kareemdev.mycocktailsdb.utils.Resource

import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class AlcoholicViewModel @Inject constructor(
    private val repository: Repository,
) :ViewModel(){

    val cocktailAlcoholic: MutableLiveData<Resource<CocktailList>> = MutableLiveData()

    fun getAlcoholic() = viewModelScope.launch {
        cocktailAlcoholic.postValue(Resource.Loading())
        val response = repository.getAlcoholic()
        cocktailAlcoholic.postValue(response)
    }
}