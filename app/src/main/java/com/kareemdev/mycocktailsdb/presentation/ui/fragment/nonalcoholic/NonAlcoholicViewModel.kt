package com.kareemdev.mycocktailsdb.presentation.ui.fragment.nonalcoholic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.mycocktailsdb.data.model.CocktailList
import com.kareemdev.mycocktailsdb.data.repositories.Repository
import com.kareemdev.mycocktailsdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NonAlcoholicViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){
    private var _cocktailNonAlcoholic = MutableLiveData<Resource<CocktailList>>()
    val cocktailNonAlcoholic: MutableLiveData<Resource<CocktailList>> get() = _cocktailNonAlcoholic

    fun getNonAlcoholic() = viewModelScope.launch {
        cocktailNonAlcoholic.postValue(Resource.Loading())
        val response = repository.getNonAlcoholic()
        cocktailNonAlcoholic.postValue(response)
    }
}