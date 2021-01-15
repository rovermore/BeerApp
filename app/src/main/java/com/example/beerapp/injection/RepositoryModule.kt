package com.example.beerapp.injection


import com.example.beerapp.client.RetrofitDataSource
import com.example.beerapp.repository.RepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun repository(retrofitDataSource: RetrofitDataSource
    ): RepositoryImpl =
        RepositoryImpl(retrofitDataSource)
}