package com.example.beerapp.injection

import com.example.beerapp.repository.RepositoryImpl
import com.example.beerapp.usecase.GetBeerDetailUseCase
import com.example.beerapp.usecase.SearchBeerUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun searchBeerUseCase(repositoryImpl: RepositoryImpl): SearchBeerUseCase =
        SearchBeerUseCase(repositoryImpl)

    @Provides
    fun getBeerDetailUseCase(repositoryImpl: RepositoryImpl): GetBeerDetailUseCase =
        GetBeerDetailUseCase(repositoryImpl)


}