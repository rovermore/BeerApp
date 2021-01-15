package com.example.beerapp.usecase

interface UseCaseWithParameter<T, P> {
    suspend fun  requestWithParameter(p: P): T
}