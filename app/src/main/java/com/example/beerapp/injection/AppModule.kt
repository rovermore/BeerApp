package com.example.beerapp.injection

import android.content.Context
import com.example.beerapp.BeerApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: BeerApp) {

    @Provides
    @Singleton
    fun context(): Context = app.applicationContext

}