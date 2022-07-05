package com.kareemdev.mycocktailsdb.presentation.ui.fragment.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kareemdev.mycocktailsdb.data.model.DrinkList
import com.kareemdev.mycocktailsdb.data.model.DrinkPreview
import com.kareemdev.mycocktailsdb.data.repositories.Repository
import com.kareemdev.mycocktailsdb.utils.Event
import com.kareemdev.mycocktailsdb.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
): ViewModel(){
    private val _drinkLiveData= MutableLiveData<Event<Resource<DrinkList>>>()
    val drinkLiveData: LiveData<Event<Resource<DrinkList>>> get() = _drinkLiveData

    private var _drinkPreviewLiveData = MutableLiveData<Event<Resource<DrinkPreview>>>()
    val drinkPreviewLiveData: LiveData<Event<Resource<DrinkPreview>>> get() = _drinkPreviewLiveData

    fun getDrinkDetail(id:String) = viewModelScope.launch {
        _drinkLiveData.postValue(Event(Resource.Loading()))
        val response = repository.getSearch(id)
        _drinkLiveData.postValue(Event(response))
    }

    fun favoriteCocktail(drink: DrinkPreview) = viewModelScope.launch {
        if(drink.strDrink.isNotEmpty() || drink.strDrinkThumb.isNullOrEmpty()){
            _drinkPreviewLiveData.postValue(Event(Resource.Error("Error")))
        }else{
            repository.upsert(drink)
            _drinkPreviewLiveData.postValue(Event(Resource.Success(drink)))
        }
    }
}