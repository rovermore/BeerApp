package com.example.beerapp.injection

import com.example.beerapp.screen.detail.DetailFragment
import com.example.beerapp.screen.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(detailFragment: DetailFragment)
}