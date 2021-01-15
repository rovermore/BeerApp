package com.example.beerapp.client

import com.example.beerapp.model.canon.Beer

interface ApiDataSource {

    suspend fun searchBeer(beerName: String, page: Int): List<Beer>?
    suspend fun getBeerById(id: Int): Beer?

}