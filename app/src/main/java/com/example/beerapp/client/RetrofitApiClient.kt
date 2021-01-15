package com.example.beerapp.client

import com.example.beerapp.model.api.BeerDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiClient {

    @GET("beers")
    suspend fun searchBeer(@Query("beer_name") beerName: String,
                           @Query("page") page: Int)
            : List<BeerDTO>?

    @GET("beers/{id}")
    suspend fun getBeerById(@Path("id") id: Int): List<BeerDTO?>

}