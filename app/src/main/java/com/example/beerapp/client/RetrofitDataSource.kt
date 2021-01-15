package com.example.beerapp.client


import com.example.beerapp.model.canon.Beer
import com.example.beerapp.model.toBeer
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val retrofitApiClient: RetrofitApiClientImpl): ApiDataSource {

    override suspend fun searchBeer(beerName: String, page: Int): List<Beer>? {
        val beerList = mutableListOf<Beer>()
        val beerDTOList = retrofitApiClient.searchBeer(beerName, page)
        beerDTOList?.let {
            for (beerDTO in beerDTOList) {
                beerList.add(beerDTO.toBeer())
            }
        }
        return beerList
    }

    override suspend fun getBeerById(id: Int): Beer? {
        return retrofitApiClient.getBeerById(id)[0]?.toBeer()
    }

}