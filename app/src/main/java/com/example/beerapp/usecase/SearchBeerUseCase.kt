package com.example.beerapp.usecase

import com.example.beerapp.model.canon.Beer
import com.example.beerapp.repository.RepositoryImpl
import javax.inject.Inject

class SearchBeerUseCase
    @Inject constructor(private val repositoryImpl: RepositoryImpl)
    : UseCaseWithParameter<List<Beer>?,Pair<String,Int> >{

    override suspend fun requestWithParameter(p: Pair<String, Int>): List<Beer>? {
        return repositoryImpl.searchBeer(p.first,p.second)
    }
}