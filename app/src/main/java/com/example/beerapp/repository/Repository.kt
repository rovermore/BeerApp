package com.example.beerapp.repository

import com.example.beerapp.model.canon.Beer


interface Repository {

    suspend fun searchBeer(beerName: String, page: Int): List<Beer>?
    suspend fun getBeerById(id: Int): Beer?
}