package com.example.beerapp.client

import com.example.beerapp.model.api.BeerDTO
import javax.inject.Inject

class RetrofitApiClientImpl
    @Inject constructor(private val retrofitApiClient: RetrofitApiClient):
    RetrofitApiClient {
    override suspend fun searchBeer(beerName: String, page: Int): List<BeerDTO>? {
        return retrofitApiClient.searchBeer(beerName, page)
    }

    override suspend fun getBeerById(id: Int): List<BeerDTO?> {
        return retrofitApiClient.getBeerById(id)
    }

}