package com.kareemdev.mycocktailsdb.presentation.ui.fragment.glass

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
class GlassViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel(){
    val cocktailGlass: MutableLiveData<Resource<CocktailList>> = MutableLiveData()
    fun getGlassCocktail() = viewModelScope.launch {
        cocktailGlass.postValue(Resource.Loading())
        val response = repository.getGlass()
        cocktailGlass.postValue(response)
    }
}