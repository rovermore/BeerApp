package com.example.beerapp.usecase

import com.example.beerapp.model.canon.Beer
import com.example.beerapp.repository.RepositoryImpl
import javax.inject.Inject

class GetBeerDetailUseCase
@Inject constructor(private val repositoryImpl: RepositoryImpl): UseCaseWithParameter<Beer?, Int> {

    override suspend fun requestWithParameter(p: Int): Beer? =
        repositoryImpl.getBeerById(p)
}