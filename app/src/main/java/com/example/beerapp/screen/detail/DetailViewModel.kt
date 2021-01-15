package com.example.beerapp.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.model.canon.Beer
import com.example.beerapp.usecase.GetBeerDetailUseCase
import com.example.beerapp.utils.ViewState
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class DetailViewModel
    @Inject constructor(private val getBeerDetailUseCase: GetBeerDetailUseCase) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<Beer>>()
    val viewState: LiveData<ViewState<Beer>>
        get() = _viewState

    fun initialize(beerId: Int) {
        loadData(beerId)
    }

    private fun loadData(beerId: Int) {
        _viewState.value = ViewState.Loading
        observeResponse(beerId)
    }

    private fun observeResponse(beerId: Int) {
        viewModelScope.launch {
            try {
                val beer = getBeerDetailUseCase.requestWithParameter(beerId)
                if (beer != null)
                    _viewState.value = ViewState.Content(beer)
                else
                    _viewState.value = ViewState.Error
            } catch (e: Exception) {
                _viewState.value = ViewState.Error
            }
        }
    }
}