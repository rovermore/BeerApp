package com.example.beerapp.repository

import com.example.beerapp.client.RetrofitDataSource
import com.example.beerapp.model.canon.Beer
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val retrofitDataSource: RetrofitDataSource)
    : Repository {

    override suspend fun searchBeer(beerName: String, page: Int): List<Beer>? {
        return retrofitDataSource.searchBeer(beerName, page)
    }

    override suspend fun getBeerById(id: Int): Beer? {
        return retrofitDataSource.getBeerById(id)
    }

}