package com.example.beerapp.screen.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerapp.model.canon.Beer
import com.example.beerapp.usecase.SearchBeerUseCase
import com.example.beerapp.utils.ViewState
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val searchBeerUseCase: SearchBeerUseCase) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState<List<Beer>>>()
    val viewState: LiveData<ViewState<List<Beer>>>
        get() = _viewState

    fun loadData(query: String, page: Int) {
        _viewState.value = ViewState.Loading
        if (query.isNotEmpty() && query.isNotBlank())
            observeResponse(query, page)
        else
            _viewState.value = ViewState.FinishLoading
    }

    private fun observeResponse(query: String, page: Int) {
        viewModelScope.launch {
            try {
                val pair = Pair<String, Int>(query, page)
                val beerList = searchBeerUseCase.requestWithParameter(pair)
                if (beerList != null) {
                    if (beerList.isNotEmpty())
                        _viewState.value = ViewState.Content(beerList)
                    else
                        _viewState.value = ViewState.FinishLoading
                } else
                    _viewState.value = ViewState.Error
            } catch (e: Exception) {
                _viewState.value = ViewState.Error
            }

        }
    }

}