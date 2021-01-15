package com.example.beerapp.usecase

interface UseCase<T> {

    suspend fun request(): T

}